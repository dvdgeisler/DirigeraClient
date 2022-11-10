package de.dvdgeisler.iot.dirigera.client.api.model;

public class Error {
    public String error;
    public String message;

    public Error() {
    }

    public Error(final String error, final String message) {
        this.error = error;
        this.message = message;
    }
}
