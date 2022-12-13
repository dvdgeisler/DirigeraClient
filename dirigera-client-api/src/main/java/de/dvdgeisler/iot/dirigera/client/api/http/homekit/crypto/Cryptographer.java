package de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto;

public class Cryptographer {
    private final byte[] writeKey;
    private final byte[] readKey;

    public Cryptographer(final byte[] writeKey, final byte[] readKey) {
        this.writeKey = writeKey;
        this.readKey = readKey;
    }
}
