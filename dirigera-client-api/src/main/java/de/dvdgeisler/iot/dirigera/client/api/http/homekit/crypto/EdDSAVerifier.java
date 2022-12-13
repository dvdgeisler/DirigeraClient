package de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto;

import de.dvdgeisler.iot.dirigera.client.api.http.homekit.AuthenticationStore;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

import java.math.BigInteger;
import java.security.*;

import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerToBytes;

public class EdDSAVerifier {
    private final static String CURVE_TABLE_NAME = "ed25519-sha-512";

    private final EdDSAPublicKey publicKey;

    public EdDSAVerifier(final byte[] publicKey) {
        final EdDSAParameterSpec spec;
        final EdDSAPublicKeySpec pubKeySpec;

        spec = EdDSANamedCurveTable.getByName(CURVE_TABLE_NAME);
        pubKeySpec = new EdDSAPublicKeySpec(publicKey, spec);

        this.publicKey = new EdDSAPublicKey(pubKeySpec);
    }

    private EdDSAVerifier(final BigInteger publicKey) {
        this(bigIntegerToBytes(publicKey));
    }

    public EdDSAVerifier(final AuthenticationStore astore) {
        this(astore.getServerPublicKey());
    }

    public boolean verify(byte[] data, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final Signature sgr;

        sgr = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
        sgr.initVerify(this.publicKey);
        sgr.update(data);

        return sgr.verify(signature);
    }
}
