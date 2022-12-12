package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.alexa;

public class AlexaStatus {
    public AlexaDisableLink disableLink;
    public String timeStamp;
    public boolean enabled;

    public AlexaStatus() {
    }

    public AlexaStatus(final AlexaDisableLink disableLink, final String timeStamp, final boolean enabled) {
        this.disableLink = disableLink;
        this.timeStamp = timeStamp;
        this.enabled = enabled;
    }
}
