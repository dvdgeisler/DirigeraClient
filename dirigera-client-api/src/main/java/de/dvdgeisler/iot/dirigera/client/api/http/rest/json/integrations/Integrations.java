package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations;

import java.util.List;

public class Integrations {
    public IntegrationsParameters params;
    public List<IntegrationsStatus> status;

    public Integrations() {
    }

    public Integrations(final IntegrationsParameters params, final List<IntegrationsStatus> status) {
        this.params = params;
        this.status = status;
    }
}
