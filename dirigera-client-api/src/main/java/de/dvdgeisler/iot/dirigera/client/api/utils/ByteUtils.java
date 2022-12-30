package de.dvdgeisler.iot.dirigera.client.api.utils;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

public abstract class ByteUtils {

    private ByteUtils() {
    }

    public static byte[] concat(byte[]... data) {
        try {
            final ByteArrayOutputStream baos;

            baos = new ByteArrayOutputStream();
            for (byte[] buf : data)
                baos.write(buf);

            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final static long MAX_UINT32 = 0x00000000FFFFFFFFL;
    private final static long MAX_INT53 = 0x001FFFFFFFFFFFFFL;

    public static byte[] UInt53toLE(final long i) {
        final long low, high;

        assert (i >= 0 && i <= MAX_INT53); // "number out of range"

        low = i & 0xFFFFFFFFL;

        if (i > MAX_UINT32)
            high = (i - low) / (MAX_UINT32 + 1);
        else
            high = 0;

        return ByteBuffer.allocate(8)
                .order(LITTLE_ENDIAN)
                .putInt(0, (int) low)
                .putInt(4, (int) high)
                .array();
    }

    public static byte[] UInt64toLE(final long i) {
        return ByteBuffer
                .allocate(8)
                .order(LITTLE_ENDIAN)
                .putLong(0, i)
                .array();
    }

    public static byte[] UInt16toLE(final short i) {
        return ByteBuffer
                .allocate(2)
                .order(LITTLE_ENDIAN)
                .putShort(0, i)
                .array();
    }

    public static short LEtoUInt16(final byte[] b) {
        return ByteBuffer
                .wrap(b)
                .order(LITTLE_ENDIAN)
                .getShort(0);
    }

    public static String toHexString(final byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02X", bytes[i]))
                .collect(Collectors.joining(" "));

    }

    public static void move(final ByteBuf in, final ByteBuf out) {
        byte[] data;

        data = new byte[in.readableBytes()];
        in.readBytes(data);
        out.writeBytes(data);
    }
}
