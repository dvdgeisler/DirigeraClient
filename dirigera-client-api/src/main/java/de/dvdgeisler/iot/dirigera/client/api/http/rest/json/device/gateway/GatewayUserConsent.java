package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

public class GatewayUserConsent {
    public GatewayUserConsentName name;
    public GatewayUserConsentValue value;

    public GatewayUserConsent() {
    }

    public GatewayUserConsent(final GatewayUserConsentName name, final GatewayUserConsentValue value) {
        this.name = name;
        this.value = value;
    }
}
