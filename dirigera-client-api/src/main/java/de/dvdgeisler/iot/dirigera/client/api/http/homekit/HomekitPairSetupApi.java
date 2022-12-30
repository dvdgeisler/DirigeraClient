package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import at.favre.lib.crypto.HKDF;
import com.nimbusds.srp6.SRP6Exception;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.EdDSASigner;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.EdDSAVerifier;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.HomekitSRP6Client;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLV;
import de.dvdgeisler.iot.dirigera.client.api.mdns.HomekitDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Duration;

import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerFromBytes;
import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerToBytes;
import static de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLVPairSetupStep.*;
import static de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLVPairingMethod.PAIR_PIN;
import static de.dvdgeisler.iot.dirigera.client.api.utils.ByteUtils.concat;

@Component
@ComponentScan(basePackageClasses = {AuthenticationStore.class})
public class HomekitPairSetupApi extends HomekitPairApi {
    private final static Logger log = LoggerFactory.getLogger(HomekitPairSetupApi.class);

    private final static byte[] PAIR_SETUP_ENCRYPT_SALT = "Pair-Setup-Encrypt-Salt".getBytes(StandardCharsets.UTF_8);
    private final static byte[] PAIR_SETUP_ENCRYPT_INFO = "Pair-Setup-Encrypt-Info".getBytes(StandardCharsets.UTF_8);
    private final static byte[] PAIR_SETUP_CONTROLLER_SIGN_SALT = "Pair-Setup-Controller-Sign-Salt".getBytes(StandardCharsets.UTF_8);
    private final static byte[] PAIR_SETUP_CONTROLLER_SIGN_INFO = "Pair-Setup-Controller-Sign-Info".getBytes(StandardCharsets.UTF_8);
    private final static byte[] PAIR_SETUP_ACCESSORY_SIGN_SALT = "Pair-Setup-Accessory-Sign-Salt".getBytes(StandardCharsets.UTF_8);
    private final static byte[] PAIR_SETUP_ACCESSORY_SIGN_INFO = "Pair-Setup-Accessory-Sign-Info".getBytes(StandardCharsets.UTF_8);
    private final static String PAIR_SETUP_IDENTITY = "Pair-Setup";

    private final HomekitSRP6Client srp;
    private final AuthenticationStore astore;
    private final String pin;

    public HomekitPairSetupApi(
            final HomekitDiscovery discovery,
            final AuthenticationStore astore,
            @Value("${dirigera.homekit.pin}") final String pin) {
        super(discovery, "pair-setup");
        this.pin = pin;
        this.srp = new HomekitSRP6Client();
        this.astore = astore;
    }

    public Mono<Void> pair() {
        return this.start()
                .delayElement(Duration.ofSeconds(1))
                .flatMap(this::verify)
                .delayElement(Duration.ofSeconds(1))
                .flatMap(this::keyExchange)
                .delayElement(Duration.ofSeconds(1))
                .flatMap(this::save)
                .then();
    }

    public Mono<Void> pairIfRequired() {
        if (this.astore.isValid())
            return Mono.empty();
        return this.pair();
    }

    private Mono<DirigeraTLV> start() {
        return Mono.fromSupplier(() -> {
                    final DirigeraTLV message;

                    log.debug("Prepare start authentication request");
                    message = new DirigeraTLV();
                    message.addMethod(PAIR_PIN);
                    message.addPairSetupStep(START_REQUEST);

                    return message;
                })
                .flatMap(message -> {
                    log.debug("Send start authentication request");
                    return this.send(message);
                });
    }

    private Mono<DirigeraTLV> verify(final DirigeraTLV startResponse) {
        return Mono.fromSupplier(() -> {
                    final DirigeraTLV message;

                    log.debug("Prepare verify request");
                    startResponse.assertStep(START_RESPONSE);

                    try {
                        this.srp.step1(PAIR_SETUP_IDENTITY, this.pin);
                        this.srp.step2(startResponse.getSalt(), startResponse.getPublicKey());
                    } catch (SRP6Exception e) {
                        throw new RuntimeException(e);
                    }

                    message = new DirigeraTLV();
                    message.addMethod(PAIR_PIN);
                    message.addPairSetupStep(VERIFY_REQUEST);
                    message.addPublicKey(this.srp.getPublicClientValue()); //A
                    message.addProof(this.srp.getClientEvidenceMessage()); //M1
                    return message;
                })
                .flatMap(message -> {
                    log.debug("Send verify request");
                    return this.send(message);
                });
    }

    private Mono<DirigeraTLV> keyExchange(final DirigeraTLV verifyResponse) {
        return Mono.fromSupplier(() -> {
                    final DirigeraTLV message;
                    final EdDSASigner signer;
                    final byte[] encryptionKey;
                    final byte[] hash;
                    final byte[] username;
                    final byte[] publicKey;
                    final byte[] material;
                    final byte[] signature;

                    log.debug("Prepare key exchange request");
                    verifyResponse.assertStep(VERIFY_RESPONSE);

                    try {
                        this.srp.step3(verifyResponse.getProof());

                        signer = new EdDSASigner(this.astore);

                        encryptionKey = HKDF.fromHmacSha512().extractAndExpand(
                                PAIR_SETUP_ENCRYPT_SALT,
                                srp.getSessionKeyHash(),
                                PAIR_SETUP_ENCRYPT_INFO,
                                32);

                        // dirigera sends an encrypted payload in its stage4 response.
                        // however its value/purpose is undocumented and unused.

                        verifyResponse.decrypt(encryptionKey, "PS-Msg04");

                        hash = HKDF.fromHmacSha512().extractAndExpand(
                                PAIR_SETUP_CONTROLLER_SIGN_SALT,
                                srp.getSessionKeyHash(),
                                PAIR_SETUP_CONTROLLER_SIGN_INFO,
                                32);

                        username = this.astore.getClientUsername().getBytes(StandardCharsets.UTF_8);
                        publicKey = signer.getPublicKey();

                        material = concat(hash, username, publicKey);
                        signature = signer.sign(material);

                        message = new DirigeraTLV();
                        message.addUsername(this.astore.getClientUsername());
                        message.addPublicKey(bigIntegerFromBytes(publicKey));
                        message.addSignature(signature);
                        message.encrypt(encryptionKey, "PS-Msg05");
                        message.addMethod(PAIR_PIN);
                        message.addPairSetupStep(KEY_EXCHANGE_REQUEST);
                    } catch (IOException |
                             NoSuchAlgorithmException |
                             SignatureException |
                             InvalidKeyException |
                             SRP6Exception e) {
                        throw new RuntimeException(e);
                    }

                    return message;
                })
                .flatMap(message -> {
                    log.debug("Send key exchange request");
                    return this.send(message);
                });
    }

    private Mono<Void> save(final DirigeraTLV keyExchangeResponse) {
        return Mono.fromSupplier(() -> {
                    try {
                        final byte[] encryptionKey;
                        final DirigeraTLV decrypted;
                        final byte[] hash;
                        final byte[] material;
                        final byte[] username;
                        final byte[] signature;
                        final byte[] serverPublicKey;
                        final EdDSAVerifier verifier;

                        keyExchangeResponse.assertStep(KEY_EXCHANGE_RESPONSE);

                        encryptionKey = HKDF.fromHmacSha512().extractAndExpand(
                                PAIR_SETUP_ENCRYPT_SALT,
                                srp.getSessionKeyHash(),
                                PAIR_SETUP_ENCRYPT_INFO,
                                32);

                        decrypted = new DirigeraTLV();
                        decrypted.add(keyExchangeResponse);
                        decrypted.decrypt(encryptionKey, "PS-Msg06");

                        hash = HKDF.fromHmacSha512().extractAndExpand(
                                PAIR_SETUP_ACCESSORY_SIGN_SALT,
                                srp.getSessionKeyHash(),
                                PAIR_SETUP_ACCESSORY_SIGN_INFO,
                                32);
                        username = decrypted.getUsername().getBytes(StandardCharsets.UTF_8);
                        signature = decrypted.getSignature();
                        serverPublicKey = bigIntegerToBytes(decrypted.getPublicKey());
                        material = concat(hash, username, serverPublicKey);
                        verifier = new EdDSAVerifier(serverPublicKey);

                        if (!verifier.verify(material, signature))
                            throw new SignatureException("Server message provides wrong signature");

                        return decrypted;
                    } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnSuccess(m -> {
                    try {
                        this.astore.setServerUsername(m.getUsername());
                        this.astore.setServerPublicKey(m.getPublicKey());
                        this.astore.save();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .then();
    }
}
