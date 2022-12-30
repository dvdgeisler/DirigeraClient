package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import at.favre.lib.crypto.HKDF;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.Cryptographer;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.EdDSASigner;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.EdDSAVerifier;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLV;
import de.dvdgeisler.iot.dirigera.client.api.mdns.HomekitDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.utils.ByteUtils;
import djb.Curve25519;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Duration;
import java.util.Random;

import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerFromBytes;
import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerToBytes;
import static de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLVPairVerificationStep.*;
import static de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLVPairingMethod.PAIR_PIN;

@Component
public class HomekitPairVerificationApi extends HomekitPairApi {
    private final static Logger log = LoggerFactory.getLogger(HomekitPairVerificationApi.class);
    private final static byte[] PAIR_VERIFY_ENCRYPT_SALT = "Pair-Verify-Encrypt-Salt".getBytes(StandardCharsets.UTF_8);
    private final static byte[] PAIR_VERIFY_ENCRYPT_INFO = "Pair-Verify-Encrypt-Info".getBytes(StandardCharsets.UTF_8);

    private final static byte[] CONTROL_ENCRYPTION_SALT = "Control-Salt".getBytes(StandardCharsets.UTF_8);
    private final static byte[] CONTROL_READ_ENCRYPTION_INFO = "Control-Read-Encryption-Key".getBytes(StandardCharsets.UTF_8);
    private final static byte[] CONTROL_WRITE_ENCRYPTION_INFO = "Control-Write-Encryption-Key".getBytes(StandardCharsets.UTF_8);

    private final HomekitPairSetupApi pairSetupApi;
    private final AuthenticationStore astore;
    private final byte[] privateKey;
    private final byte[] publicKey;
    private final Cryptographer cryptographer;

    public HomekitPairVerificationApi(final HomekitDiscovery discovery,
                                      final HomekitPairSetupApi pairSetupApi,
                                      final AuthenticationStore astore,
                                      final Cryptographer cryptographer) {
        super(discovery, "pair-verify");
        this.pairSetupApi = pairSetupApi;
        this.astore = astore;

        this.privateKey = new byte[32];
        this.publicKey = new byte[32];

        new Random().nextBytes(this.privateKey);
        Curve25519.keygen(this.publicKey, null, this.privateKey);

        this.cryptographer = cryptographer;
    }

    private Mono<DirigeraTLV> start() {
        return this.pairSetupApi.pairIfRequired()
                .then(Mono.fromSupplier(() -> {
                    final DirigeraTLV message;

                    log.debug("Prepare start verification request");
                    message = new DirigeraTLV();
                    //message.addMethod(PAIR_PIN);
                    message.addPairVerificationStep(START_REQUEST);
                    message.addPublicKey(bigIntegerFromBytes(this.publicKey));

                    return message;
                }))
                .flatMap(message -> {
                    log.debug("Send start verification request");
                    return this.send(message);
                });
    }

    private Mono<Void> finish(final DirigeraTLV startResponse) {
        return Mono.fromSupplier(() -> {
                    // decrypt message
                    try {
                        final byte[] sharedSecret;
                        final byte[] encryptionKey;
                        final DirigeraTLV decrypted;

                        startResponse.assertStep(START_RESPONSE);

                        sharedSecret = new byte[32];
                        Curve25519.curve(sharedSecret, this.privateKey, bigIntegerToBytes(startResponse.getPublicKey()));

                        encryptionKey = HKDF.fromHmacSha512().extractAndExpand(
                                PAIR_VERIFY_ENCRYPT_SALT,
                                sharedSecret,
                                PAIR_VERIFY_ENCRYPT_INFO,
                                32);

                        decrypted = new DirigeraTLV();
                        decrypted.add(startResponse);
                        decrypted.decrypt(encryptionKey, "PV-Msg02");

                        return decrypted;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(decrypted -> {
                    // verify message signature
                    try {
                        final String username;
                        final byte[] signature;
                        final byte[] material;
                        final EdDSAVerifier verifier;

                        log.debug("Verify username and signature");

                        username = decrypted.getUsername();
                        signature = decrypted.getSignature();

                        if (!username.equals(this.astore.getServerUsername()))
                            throw new IllegalAccessException(String.format(
                                    "Server user \"%s\" does not match with authentication store: \"%s\"",
                                    username,
                                    this.astore.getServerUsername()));

                        material = ByteUtils.concat(
                                bigIntegerToBytes(startResponse.getPublicKey()),
                                username.getBytes(StandardCharsets.UTF_8),
                                this.publicKey);
                        verifier = new EdDSAVerifier(this.astore);
                        if (!verifier.verify(material, signature))
                            throw new SignatureException("Server message provides wrong signature");

                        return decrypted;
                    } catch (IllegalAccessException |
                             NoSuchAlgorithmException |
                             InvalidKeyException |
                             SignatureException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(decrypted -> {
                    // create response
                    try {
                        final byte[] sharedSecret;
                        final byte[] encryptionKey;
                        final byte[] material;
                        final byte[] signature;
                        final EdDSASigner signer;
                        final DirigeraTLV message;

                        log.debug("Prepare start verification request");

                        sharedSecret = new byte[32];
                        Curve25519.curve(sharedSecret, this.privateKey, bigIntegerToBytes(startResponse.getPublicKey()));

                        encryptionKey = HKDF.fromHmacSha512().extractAndExpand(
                                PAIR_VERIFY_ENCRYPT_SALT,
                                sharedSecret,
                                PAIR_VERIFY_ENCRYPT_INFO,
                                32);

                        signer = new EdDSASigner(this.astore);
                        material = ByteUtils.concat(
                                this.publicKey,
                                this.astore.getClientUsername().getBytes(StandardCharsets.UTF_8),
                                bigIntegerToBytes(decrypted.getPublicKey()));
                        signature = signer.sign(material);

                        message = new DirigeraTLV();
                        message.addUsername(this.astore.getClientUsername());
                        message.addSignature(signature);
                        message.encrypt(encryptionKey, "PV-Msg03");
                        message.addMethod(PAIR_PIN);
                        message.addPairVerificationStep(FINISH_REQUEST);

                        return message;
                    } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(message -> {
                    log.debug("Send finish verification request");
                    return this.send(message);
                })
                .doOnSuccess(finishResponse -> {
                    final byte[] sharedSecret;
                    final byte[] readKey;
                    final byte[] writeKey;

                    finishResponse.assertStep(FINISH_RESPONSE);

                    sharedSecret = new byte[32];
                    Curve25519.curve(sharedSecret, this.privateKey, bigIntegerToBytes(startResponse.getPublicKey()));

                    readKey = HKDF.fromHmacSha512().extractAndExpand(
                            CONTROL_ENCRYPTION_SALT,
                            sharedSecret,
                            CONTROL_READ_ENCRYPTION_INFO,
                            32);

                    writeKey = HKDF.fromHmacSha512().extractAndExpand(
                            CONTROL_ENCRYPTION_SALT,
                            sharedSecret,
                            CONTROL_WRITE_ENCRYPTION_INFO,
                            32);

                    log.info("Pair verification was successful. Cryptographer is set up.");
                    this.cryptographer.enable(writeKey, readKey);
                })
                .then();
    }

    public Mono<Cryptographer> getCryptographer() {
        return Mono.just(this.cryptographer)
                .filter(Cryptographer::isEnabled)
                .switchIfEmpty(this.start()
                        .delayElement(Duration.ofSeconds(1))
                        .flatMap(this::finish)
                        .then(Mono.just(this.cryptographer))
                        .filter(Cryptographer::isEnabled)
                        .switchIfEmpty(Mono.error(new IOException("Cannot initialize cryptographer"))));
    }
}
