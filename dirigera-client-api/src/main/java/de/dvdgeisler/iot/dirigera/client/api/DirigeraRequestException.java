package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.model.Error;

import java.io.IOException;

public class DirigeraRequestException extends IOException {

    public DirigeraRequestException(final String error, final String message) {
        super(String.format("Error while requesting Dirigera: error=%s, message=%s",error,message));
    }

    public DirigeraRequestException(final Error error) {
        this(error.error, error.message);
    }

    public DirigeraRequestException(final String message) {
        super(String.format("Error while requesting Dirigera: message=%s", message));
    }
}
