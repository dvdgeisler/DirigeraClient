package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.integrations.google;

public class GoogleParameters {
    public String clientID;
    public String deepLinkURL;

    public GoogleParameters(final String clientID, final String deepLinkURL) {
        this.clientID = clientID;
        this.deepLinkURL = deepLinkURL;
    }

    public GoogleParameters() {
    }
}
