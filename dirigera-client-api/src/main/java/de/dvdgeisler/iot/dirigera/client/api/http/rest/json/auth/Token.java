package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.auth;

public class Token {
    public String access_token;

    public Token() {
    }

    public Token(final String access_token) {
        this.access_token = access_token;
    }
}
