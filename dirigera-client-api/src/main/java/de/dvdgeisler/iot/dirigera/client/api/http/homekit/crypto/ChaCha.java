package de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.Poly1305;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import static de.dvdgeisler.iot.dirigera.client.api.utils.ByteUtils.concat;

public class ChaCha {
    private static final int HMAC_LENGTH = 16;
    private static final int CHA_CHA_ROUNDS = 20;

    private final ChaChaEngine engine;

    public ChaCha(final ChaChaEngine engine, final CipherParameters parameters) {
        this.engine = engine;

        this.engine.init(true, parameters);
    }

    public ChaCha(final ChaChaEngine engine, final KeyParameter key, final byte[] nonce) {
        this(engine, new ParametersWithIV(key, nonce));
    }

    public ChaCha(final ChaChaEngine engine, final byte[] key, final byte[] nonce) {
        this(engine, new KeyParameter(key), nonce);
    }

    public ChaCha(final int rounds, final byte[] key, final byte[] nonce) {
        this(new ChaChaEngine(rounds), key, nonce);
    }

    public ChaCha(final byte[] key, final byte[] nonce) {
        this(CHA_CHA_ROUNDS, key, nonce);
    }

    public byte[] encrypt(final byte[] plaintext) throws IOException {
        return this.encrypt(plaintext, null);
    }

    public byte[] encrypt(final byte[] plaintext, final byte[] additionalData) throws IOException {
        final KeyParameter macKey;
        final byte[] ciphertext;
        final byte[] hmac;
        final byte[] output;

        macKey = initRecordMAC(this.engine);

        ciphertext = new byte[plaintext.length];
        this.engine.processBytes(plaintext, 0, plaintext.length, ciphertext, 0);

        hmac = createPolyKey(macKey, additionalData, ciphertext);
        output = concat(ciphertext, hmac);
        return output;
    }

    public byte[] decrypt(byte[] encrypted) throws IOException {
        final byte[] ciphertext;
        final byte[] hmac;

        if (encrypted.length < HMAC_LENGTH)
            throw new IllegalArgumentException(String.format("Encrypted buffer must provide at least %d bytes for hmac", HMAC_LENGTH));


        ciphertext = new byte[encrypted.length - HMAC_LENGTH];
        hmac = new byte[HMAC_LENGTH];
        System.arraycopy(encrypted, 0, ciphertext, 0, ciphertext.length);
        System.arraycopy(encrypted, ciphertext.length, hmac, 0, hmac.length);


        return this.decrypt(hmac, null, ciphertext);
    }

    public byte[] decrypt(byte[] hmac, byte[] ciphertext) throws IOException {
        return this.decrypt(hmac, null, ciphertext);
    }

    public byte[] decrypt(byte[] hmac, byte[] additionalData, byte[] ciphertext) throws IOException {
        final KeyParameter macKey;
        final byte[] calculatedMAC;
        final byte[] output;

        macKey = initRecordMAC(this.engine);

        calculatedMAC = createPolyKey(macKey, additionalData, ciphertext);

        if (!Arrays.constantTimeAreEqual(calculatedMAC, hmac))
            throw new IOException("Bad record mac");

        output = new byte[ciphertext.length];
        this.engine.processBytes(ciphertext, 0, ciphertext.length, output, 0);

        return output;
    }

    private KeyParameter initRecordMAC(final ChaChaEngine cipher) {
        final byte[] firstBlock;

        firstBlock= new byte[64];
        cipher.processBytes(firstBlock, 0, firstBlock.length, firstBlock, 0);

        // NOTE: The BC implementation puts 'r' after 'k'
        System.arraycopy(firstBlock, 0, firstBlock, 32, 16);
        KeyParameter macKey = new KeyParameter(firstBlock, 16, 32);
        Poly1305KeyGenerator.clamp(macKey.getKey());
        return macKey;
    }

    public static byte[] createPolyKey(final KeyParameter macKey, final byte[] additionalData, final byte[] ciphertext) {
        final Poly1305 poly;
        final Consumer<byte[]> padding;
        final byte[] additionalDataLength;
        final byte[] ciphertextLength;
        final byte[] calculatedMAC;

        poly = new Poly1305();
        poly.init(macKey);

        padding = d -> {
            final int round;
            poly.update(d, 0, d.length);
            if (d.length % 16 != 0) {
                round = 16 - (d.length % 16);
                poly.update(new byte[round], 0, round);
            }
        };

        Optional.ofNullable(additionalData).ifPresent(padding);
        Optional.ofNullable(ciphertext).ifPresent(padding);

        additionalDataLength = Optional.ofNullable(additionalData)
                .map(d -> d.length)
                .map(Pack::longToLittleEndian)
                .orElse(new byte[8]);
        poly.update(additionalDataLength, 0, 8);

        ciphertextLength = Optional.ofNullable(ciphertext)
                .map(d -> d.length)
                .map(Pack::longToLittleEndian)
                .orElse(new byte[8]);;
        poly.update(ciphertextLength, 0, 8);

        calculatedMAC = new byte[poly.getMacSize()];
        poly.doFinal(calculatedMAC, 0);

        return calculatedMAC;
    }

    public static byte[] encryptData(final byte[] data, final byte[] key, final byte[] nonce) throws IOException {
        return new ChaCha(key, nonce).encrypt(data);
    }

    public static byte[] encryptData(final byte[] data, final byte[] additionalData, final byte[] key, final byte[] nonce) throws IOException {
        return new ChaCha(key, nonce).encrypt(data, additionalData);
    }

    public static byte[] decryptData(final byte[] encrypted, final byte[] key, final byte[] nonce) throws IOException {
        return new ChaCha(key, nonce).decrypt(encrypted);
    }
}
