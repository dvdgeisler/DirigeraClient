package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import com.nimbusds.srp6.BigIntegerUtils;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.ChaCha;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static com.nimbusds.srp6.BigIntegerUtils.bigIntegerFromBytes;
import static de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLVTag.*;

public class DirigeraTLV extends TLV<DirigeraTLVTag> {
    public DirigeraTLV() {
        super();
    }

    public DirigeraTLV(final byte[] bytes) throws IOException {
        super(bytes);
    }

    public void addMethod(final DirigeraTLVPairingMethod method) {
        this.add(PAIRING_METHOD, method.encode());
    }

    public DirigeraTLVPairingMethod getMethod() {
        return DirigeraTLVPairingMethod.decode(this.get(PAIRING_METHOD));
    }

    public void addUsername(final String username) {
        this.add(USERNAME, username.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsername() {
        return new String(this.get(USERNAME), StandardCharsets.UTF_8);
    }

    public void addSalt(final BigInteger salt) {
        this.add(SALT, BigIntegerUtils.bigIntegerToBytes(salt));
    }

    public BigInteger getSalt() {
        return bigIntegerFromBytes(this.get(SALT));
    }

    public void addPublicKey(final BigInteger publicKey) {
        this.add(PUBLIC_KEY, BigIntegerUtils.bigIntegerToBytes(publicKey));
    }

    public BigInteger getPublicKey() {
        return bigIntegerFromBytes(this.get(PUBLIC_KEY));
    }

    public void addProof(final BigInteger proof) {
        this.add(PROOF, BigIntegerUtils.bigIntegerToBytes(proof));
    }

    public BigInteger getProof() {
        return bigIntegerFromBytes(this.get(PROOF));
    }

    public void addEncryptedData(final byte[] data) {
        this.add(ENCRYPTED_DATA, data);
    }

    public byte[] getEncryptedData() {
        return this.get(ENCRYPTED_DATA);
    }

    public void addPairSetupStep(final DirigeraTLVPairSetupStep state) {
        this.add(SEQUENCE, state.encode());
    }

    public DirigeraTLVPairSetupStep getPairSetupStep() {
        return DirigeraTLVPairSetupStep.decode(this.get(SEQUENCE));
    }


    public void addPairVerificationStep(final DirigeraTLVPairVerificationStep state) {
        this.add(SEQUENCE, state.encode());
    }

    public DirigeraTLVPairVerificationStep getPairVerificationStep() {
        return DirigeraTLVPairVerificationStep.decode(this.get(SEQUENCE));
    }

    public void addError(final DirigeraTLVError error) {
        this.add(ERROR_CODE, error.encode());
    }

    public DirigeraTLVError getError() {
        return DirigeraTLVError.decode(this.get(ERROR_CODE));
    }

    public void addSignature(final byte[] signature) {
        this.add(SIGNATURE, signature);
    }

    public byte[] getSignature() {
        return this.get(SIGNATURE);
    }

    @Override
    protected void encodeTag(final DirigeraTLVTag tag, final OutputStream os) throws IOException {
        DirigeraTLVTag.encode(tag, os);
    }

    @Override
    protected DirigeraTLVTag decodeTag(final InputStream os) throws IOException {
        return DirigeraTLVTag.decode(os);
    }

    public void encrypt(final byte[] key, final String nonce) throws IOException {
        final byte[] ciphertext;

        ciphertext = ChaCha.encryptData(this.encode(), key, nonce.getBytes(StandardCharsets.UTF_8));
        this.clear();
        this.addEncryptedData(ciphertext);
    }

    public void decrypt(final byte[] key, final String nonce) throws IOException {
        final byte[] encryptedData;
        final byte[] decryptedData;

        encryptedData = this.getEncryptedData();
        decryptedData = ChaCha.decryptData(encryptedData, key, nonce.getBytes(StandardCharsets.UTF_8));
        this.decode(decryptedData);
    }

    public void checkForError() throws IOException {
        final DirigeraTLVError error;

        error = this.getError();
        if (error == null || error.equals(DirigeraTLVError.NONE))
            return;

        throw new IOException(String.format("Dirigera TLV message contains error code: %s", error));
    }

    public void assertStep(final DirigeraTLVPairSetupStep step) {
        assertStep(step, this.getPairSetupStep());
    }

    public void assertStep(final DirigeraTLVPairVerificationStep step) {
        assertStep(step, this.getPairVerificationStep());
    }

    private static <Step> void assertStep(final Step expected, final Step actual) {
        if (!actual.equals(expected))
            throw new IllegalStateException(
                    String.format(
                            "Pairing failed. Expected state %s, got %s",
                            expected,
                            actual));
    }
}
