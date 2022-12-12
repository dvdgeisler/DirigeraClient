package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.alexa;

public class AlexaDisableLink {
    public String androidApp;
    public String fallback;

    public AlexaDisableLink() {
    }

    public AlexaDisableLink(final String androidApp, final String fallback) {
        this.androidApp = androidApp;
        this.fallback = fallback;
    }
}
