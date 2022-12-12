package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.alexa.AlexaStatus;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.google.GoogleStatus;

public class IntegrationsStatus {
    public AlexaStatus alexa;
    public GoogleStatus google;
    public String userId;

    public IntegrationsStatus() {
    }

    public IntegrationsStatus(final AlexaStatus alexa, final GoogleStatus google, final String userId) {
        this.alexa = alexa;
        this.google = google;
        this.userId = userId;
    }
}
