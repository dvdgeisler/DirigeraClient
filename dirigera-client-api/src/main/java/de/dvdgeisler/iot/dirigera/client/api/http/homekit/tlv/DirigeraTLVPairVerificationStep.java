package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DirigeraTLVPairVerificationStep {
    START_REQUEST((byte)0x01),
    START_RESPONSE((byte)0x02),
    FINISH_REQUEST((byte)0x03),
    FINISH_RESPONSE((byte)0x04);

    private final static Map<Byte, DirigeraTLVPairVerificationStep> code2state = Arrays.stream(DirigeraTLVPairVerificationStep.values()).collect(Collectors.toMap(tag->tag.code, tag->tag));

    private final byte code;

    DirigeraTLVPairVerificationStep(final byte code) {
        this.code = code;
    }

    public byte[] encode() {
        return new byte[]{this.code};
    }

    public static DirigeraTLVPairVerificationStep decode(final byte[] bytes) {
        return code2state.get(bytes[0]);
    }
}
