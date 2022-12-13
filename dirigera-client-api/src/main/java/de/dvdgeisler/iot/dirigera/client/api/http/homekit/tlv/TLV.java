package de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv;

import de.dvdgeisler.iot.dirigera.client.api.utils.ByteUtils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TLV<Tag> {
    private final Map<Tag, byte[]> values;

    protected TLV() {
        this.values = new LinkedHashMap<>();
    }

    public TLV(final byte[] bytes) throws IOException {
        this();
        this.decode(bytes);
    }

    public void add(final Tag tag, final byte[] bytes) {
        this.values.put(tag, bytes);
    }

    public void rem(final Tag tag) {
        this.values.remove(tag);
    }

    public byte[] get(final Tag tag) {
        return this.values.getOrDefault(tag, new byte[0]);
    }

    public void encode(final OutputStream os) throws IOException {
        ByteArrayInputStream bais;
        int length;
        byte[] data;

        for (Map.Entry<Tag, byte[]> entry: this.values.entrySet()) {
            bais = new ByteArrayInputStream(entry.getValue());
            while (bais.available() > 0) {
                length = Math.min(bais.available(), 255);
                data = bais.readNBytes(length);

                this.encodeTag(entry.getKey(), os);
                os.write(length);
                os.write(data);
            }
        }
    }

    public byte[] encode() throws IOException {
        final ByteArrayOutputStream baos;

        baos = new ByteArrayOutputStream();
        this.encode(baos);

        return baos.toByteArray();
    }

    public void decode(final InputStream is) throws IOException {
        Tag tag;
        int len;
        byte[] data;

        while(is.available() > 0) {
            tag = this.decodeTag(is);
            len = is.read();
            data = TLV.concat(this.get(tag), is.readNBytes(len));
            this.add(tag, data);
        }
    }

    public void decode(final byte[] data) throws IOException {
        this.decode(new ByteArrayInputStream(data));
    }

    private static byte[] concat(final byte[] l, final byte[] r) throws IOException {
        final ByteArrayOutputStream baos;

        baos = new ByteArrayOutputStream();
        baos.write(l);
        baos.write(r);
        return baos.toByteArray();
    }

    public void add(final TLV<Tag> tlv) {
        this.values.putAll(tlv.values);
    }
    public void clear() {
        this.values.clear();
    }

    protected abstract void encodeTag(final Tag tag, final OutputStream os) throws IOException;
    protected abstract Tag decodeTag(final InputStream os) throws IOException;

    public String toString() {
        return this.values.entrySet()
                .stream()
                .map(entry -> String.format(
                        "%20s: %s",
                        entry.getKey(),
                        ByteUtils.toHexString(entry.getValue())))
                .collect(Collectors.joining("\n"));
    }

}
