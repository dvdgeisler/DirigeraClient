package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.google;

public class GoogleAuthentication {
    public String authCode;

    public GoogleAuthentication(final String authCode) {
        this.authCode = authCode;
    }

    public GoogleAuthentication() {
    }
}
