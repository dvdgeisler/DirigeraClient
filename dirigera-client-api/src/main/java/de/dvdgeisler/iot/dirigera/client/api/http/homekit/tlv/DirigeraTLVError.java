package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DirigeraTLVError {
    NONE((byte)0x00),
    UNKNOWN((byte)0x01),
    AUTHENTICATION_FAILED((byte)0x02),
    TOO_MANY_ATTEMPTS((byte)0x03),
    UNKNOWN_PEER((byte)0x04),
    MAX_PEER((byte)0x05),
    MAX_AUTHENTICATION_ATTEMPTS((byte)0x06);

    private final static Map<Byte, DirigeraTLVError> code2state = Arrays.stream(DirigeraTLVError.values()).collect(Collectors.toMap(tag->tag.code, tag->tag));

    private final byte code;

    DirigeraTLVError(final byte code) {
        this.code = code;
    }

    public byte[] encode() {
        return new byte[]{this.code};
    }

    public static DirigeraTLVError decode(final byte[] bytes) {
        if(bytes.length == 0)
            return NONE;
        if(bytes.length > 1)
            throw new IllegalArgumentException(String.format("Error code must be 1 byte (got %d bytes)", bytes.length));
        return code2state.get(bytes[0]);
    }
}
