package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.alexa.AlexaParameters;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.google.GoogleParameters;

public class IntegrationsParameters {
    public AlexaParameters alexa;
    public GoogleParameters google;

    public IntegrationsParameters() {
    }

    public IntegrationsParameters(final AlexaParameters alexa, final GoogleParameters google) {
        this.alexa = alexa;
        this.google = google;
    }
}
