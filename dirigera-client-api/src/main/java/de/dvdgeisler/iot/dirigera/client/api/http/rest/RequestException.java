package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.Error;

import java.io.IOException;

public class RequestException extends IOException {

    public RequestException(final String error, final String message) {
        super(String.format("Error while requesting Dirigera: error=%s, message=%s",error,message));
    }

    public RequestException(final Error error) {
        this(error.error, error.message);
    }

    public RequestException(final String message) {
        super(String.format("Error while requesting Dirigera: message=%s", message));
    }
}
