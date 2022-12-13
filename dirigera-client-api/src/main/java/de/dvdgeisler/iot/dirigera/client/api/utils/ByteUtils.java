package de.dvdgeisler.iot.dirigera.client.api.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ByteUtils {

    private ByteUtils() {
    }

    public static byte[] concat(byte[]... data) throws IOException {
        final ByteArrayOutputStream baos;

        baos = new ByteArrayOutputStream();
        for (byte[] buf : data)
            baos.write(buf);

        return baos.toByteArray();
    }

    public static String toHexString(final byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02X", bytes[i]))
                .collect(Collectors.joining(" "));

    }
}
