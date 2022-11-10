package de.dvdgeisler.iot.dirigera.client.api.model.integrations;

import de.dvdgeisler.iot.dirigera.client.api.model.integrations.alexa.AlexaParameters;
import de.dvdgeisler.iot.dirigera.client.api.model.integrations.google.GoogleParameters;

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
