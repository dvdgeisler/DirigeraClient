package de.dvdgeisler.iot.dirigera.client.api.http.rest.json;

public class Endpoints {
    public String remoteApiEndpoint;
    public String remoteWsEndpoint;

    public Endpoints() {
    }

    public Endpoints(final String remoteApiEndpoint, final String remoteWsEndpoint) {
        this.remoteApiEndpoint = remoteApiEndpoint;
        this.remoteWsEndpoint = remoteWsEndpoint;
    }
}
