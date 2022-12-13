package de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto;

import de.dvdgeisler.iot.dirigera.client.api.http.homekit.AuthenticationStore;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class EdDSASigner {
    private final static String CURVE_TABLE_NAME = "ed25519-sha-512";

    private final EdDSAPublicKey publicKey;
    private final EdDSAPrivateKey privateKey;

    private EdDSASigner(final byte[] privateKeyBytes) {
        final EdDSAParameterSpec spec;
        final EdDSAPrivateKeySpec privateKeySpec;
        final EdDSAPublicKeySpec pubKeySpec;

        spec = EdDSANamedCurveTable.getByName(CURVE_TABLE_NAME);
        privateKeySpec = new EdDSAPrivateKeySpec(privateKeyBytes, spec);
        pubKeySpec = new EdDSAPublicKeySpec(privateKeySpec.getA(), spec);

        this.publicKey = new EdDSAPublicKey(pubKeySpec);
        this.privateKey = new EdDSAPrivateKey(privateKeySpec);
    }

    private EdDSASigner(final String privateKey) {
        this(privateKey.getBytes(StandardCharsets.UTF_8));
    }

    public EdDSASigner(final AuthenticationStore astore) {
        this(astore.getClientPassword());
    }

    public byte[] getPublicKey() {
        return this.publicKey.getAbyte();
    }

    public byte[] sign(byte[] material) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final Signature sgr;

        sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
        sgr.initSign(this.privateKey);
        sgr.update(material);

        return sgr.sign();
    }
}
