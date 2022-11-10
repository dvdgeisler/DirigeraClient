package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.http.json.Error;

import java.io.IOException;
import java.util.Map;

public class DirigeraRequestException extends IOException {

    public DirigeraRequestException(final String error, final String message) {
        super(String.format("Error while requesting Dirigera: error=%s, message=%s",error,message));
    }
    @Deprecated
    public DirigeraRequestException(final Map<String, Object> response) {
        this(response.getOrDefault("error", "Unknown").toString(), response.getOrDefault("message", "No error message available").toString());
    }
    public DirigeraRequestException(final Error error) {
        this(error.error, error.message);
    }
}
