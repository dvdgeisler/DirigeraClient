package de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto;

import com.nimbusds.srp6.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerFromBytes;
import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerToBytes;

public class HomekitSRP6Client
        extends SRP6ClientSession
        implements ClientEvidenceRoutine, ServerEvidenceRoutine, XRoutine, URoutine {
    private final static int BIT_SIZE = 3072;
    private final static String HASH_METHOD = "SHA-512";
    public HomekitSRP6Client(final SRP6CryptoParams config) {
        super();
        this.config = config;
        this.setClientEvidenceRoutine(this);
        this.setServerEvidenceRoutine(this);
        this.setXRoutine(this);
        this.setHashedKeysRoutine(this);
    }

    public HomekitSRP6Client() {
        this(SRP6CryptoParams.getInstance(BIT_SIZE, HASH_METHOD));
    }

    private static MessageDigest getMessageDigest(final String H) {
        try {
            return MessageDigest.getInstance(H);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not locate requested algorithm", e);
        }
    }

    /**
     * Computes M1
     *
     * @param cryptoParams The crypto parameters for the SRP-6a protocol.
     * @param ctx          Snapshot of the SRP-6a client session variables
     *                     which may be used in the computation of the
     *                     client evidence message.
     * @return
     */
    @Override
    public BigInteger computeClientEvidence(
            final SRP6CryptoParams cryptoParams,
            final SRP6ClientEvidenceContext ctx) {
        final MessageDigest digest;
        final byte[] hN;
        final byte[] hg;
        final byte[] hNhg;
        final byte[] hu;

        digest = getMessageDigest(cryptoParams.H);

        hN = digest.digest(bigIntegerToBytes(cryptoParams.N));
        hg = digest.digest(bigIntegerToBytes(cryptoParams.g));
        hNhg = xor(hN, hg);
        hu = digest.digest(ctx.userID.getBytes(StandardCharsets.UTF_8));

        digest.update(hNhg);
        digest.update(hu);
        digest.update(bigIntegerToBytes(ctx.s));
        digest.update(bigIntegerToBytes(ctx.A));
        digest.update(bigIntegerToBytes(ctx.B));
        digest.update(this.getSessionKeyHash());

        return bigIntegerFromBytes(digest.digest());
    }

    /**
     * Computes M2
     *
     * @param cryptoParams The crypto parameters for the SRP-6a protocol.
     * @param ctx          Snapshot of the SRP-6a server session variables
     *                     which may be used in the computation of the
     *                     server evidence message.
     * @return
     */
    @Override
    public BigInteger computeServerEvidence(
            final SRP6CryptoParams cryptoParams,
            final SRP6ServerEvidenceContext ctx) {
        final MessageDigest digest;

        digest = getMessageDigest(cryptoParams.H);

        digest.update(bigIntegerToBytes(ctx.A));
        digest.update(bigIntegerToBytes(ctx.M1));
        digest.update(this.getSessionKeyHash());

        return bigIntegerFromBytes(digest.digest());
    }

    /**
     * Compute the intermediate value x as a hash of four buffers:
     * salt, identity, colon, and password.
     * <p>
     * x = H(s | H(I | ":" | P))
     *
     * @param digest   The hash function 'H'. To enforce a particular hash
     *                 algorithm, e.g. "SHA-1", you may perform a check
     *                 that throws an {@code IllegalArgumentException} or
     *                 ignore this argument altogether.
     * @param salt     The salt 's'. This is considered a mandatory
     *                 argument in computation of 'x'.
     * @param username The user identity 'I'. It may be ignored if the
     *                 username is allowed to change or if a user may
     *                 authenticate with multiple alternate identities,
     *                 such as name and email address.
     * @param password The user password 'P'. This is considered a
     *                 mandatory argument in the computation of 'x'.
     * @return
     */
    @Override
    public BigInteger computeX(
            final MessageDigest digest,
            final byte[] salt,
            final byte[] username,
            final byte[] password) {
        final byte[] h0;
        final byte[] h1;

        digest.update(username);
        digest.update((byte)':'); //":"
        digest.update(password);
        h0 = digest.digest();

        digest.update(salt);
        digest.update(h0);
        h1 = digest.digest();

        return bigIntegerFromBytes(h1);
    }

    /**
     * computeU hashes the two public messages together, to obtain a scrambling
     * parameter "u" which cannot be predicted by either party ahead of time.
     * This makes it safe to use the message ordering defined in the SRP-6a
     * paper, in which the server reveals their "B" value before the client
     * commits to their "A" value.
     *
     * @param cryptoParams The crypto parameters for the SRP-6a protocol.
     * @param ctx          Snapshot of the SRP-6a client session variables
     *                     which may be used in the computation of the
     *                     hashed keys message.
     * @return
     */
    @Override
    public BigInteger computeU(
            final SRP6CryptoParams cryptoParams,
            final URoutineContext ctx) {
        final MessageDigest digest;

        digest = getMessageDigest(cryptoParams.H);
        digest.update(bigIntegerToBytes(ctx.A));
        digest.update(bigIntegerToBytes(ctx.B));

        return bigIntegerFromBytes(digest.digest());
    }

    /**
     * Compute the shared session key K from S
     *
     * @return
     */
    public byte[] getSessionKeyHash() {
        final MessageDigest digest;
        final byte[] K1;
        final byte[] K2;
        final ByteArrayOutputStream outputStream;

        if (this.S == null)
            return null;

        digest = getMessageDigest(this.config.H);

        digest.update(bigIntegerToBytes(this.S));

        if (!this.config.H.equals("SHA-1"))
            return digest.digest();

        digest.update(new byte[]{0, 0, 0, 0});
        K1 = digest.digest();

        digest.update(bigIntegerToBytes(this.S));
        digest.update(new byte[]{0, 0, 0, 1});
        K2 = digest.digest();

        outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(K1);
            outputStream.write(K2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    public SRP6ClientCredentials step2(final BigInteger s, final BigInteger B) throws SRP6Exception {
        return super.step2(this.config, s, B);
    }

    private static byte[] xor(byte[] b1, byte[] b2) {
        final byte[] result;

        result = new byte[b1.length];
        for (int i = 0; i < b1.length; i++)
            result[i] = (byte) (b1[i] ^ b2[i]);

        return result;
    }
}
