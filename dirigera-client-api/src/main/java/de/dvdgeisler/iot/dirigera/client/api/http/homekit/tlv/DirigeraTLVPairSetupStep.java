package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DirigeraTLVPairSetupStep {
    START_REQUEST((byte)0x01),
    START_RESPONSE((byte)0x02),
    VERIFY_REQUEST((byte)0x03),
    VERIFY_RESPONSE((byte)0x04),
    KEY_EXCHANGE_REQUEST((byte)0x05),
    KEY_EXCHANGE_RESPONSE((byte)0x06);

    private final static Map<Byte, DirigeraTLVPairSetupStep> code2state = Arrays.stream(DirigeraTLVPairSetupStep.values()).collect(Collectors.toMap(tag->tag.code, tag->tag));

    private final byte code;

    DirigeraTLVPairSetupStep(final byte code) {
        this.code = code;
    }

    public byte[] encode() {
        return new byte[]{this.code};
    }

    public static DirigeraTLVPairSetupStep decode(final byte[] bytes) {
        return code2state.get(bytes[0]);
    }
}
