package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DirigeraTLVPairingMethod {
    PAIR_PIN((byte) 0x01);

    private final static Map<Byte, DirigeraTLVPairingMethod> code2method = Arrays.stream(DirigeraTLVPairingMethod.values()).collect(Collectors.toMap(tag->tag.code, tag->tag));

    private final byte code;

    DirigeraTLVPairingMethod(final byte code) {
        this.code = code;
    }

    public byte[] encode() {
        return new byte[]{this.code};
    }

    public static DirigeraTLVPairingMethod decode(final byte[] bytes) {
        return code2method.get(bytes[0]);
    }
}
