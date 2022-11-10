package de.dvdgeisler.iot.dirigera.client.api.model.auth;

import java.util.UUID;

public class Authorize {
    public UUID code;

    public Authorize() {
    }

    public Authorize(final UUID code) {
        this.code = code;
    }
}
