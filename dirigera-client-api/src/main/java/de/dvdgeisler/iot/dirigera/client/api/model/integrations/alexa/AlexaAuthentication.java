package de.dvdgeisler.iot.dirigera.client.api.model.integrations.alexa;

public class AlexaAuthentication {
    public String alexaAuthCode;
    public String redirectUri;

    public AlexaAuthentication(final String alexaAuthCode, final String redirectUri) {
        this.alexaAuthCode = alexaAuthCode;
        this.redirectUri = redirectUri;
    }

    public AlexaAuthentication() {
    }
}
