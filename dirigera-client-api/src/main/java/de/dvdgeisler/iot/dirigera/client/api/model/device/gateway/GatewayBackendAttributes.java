package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GatewayBackendAttributes {
    @JsonProperty("backendConnected")
    public Boolean connected;
    @JsonProperty("backendConnectionPersistent")
    public Boolean connectionPersistent;
    @JsonProperty("backendOnboardingComplete")
    public Boolean onboardingComplete;
    @JsonProperty("backendRegion")
    public String region;
    @JsonProperty("backendCountryCode")
    public String countryCode;

    public GatewayBackendAttributes(final Boolean connected, final Boolean connectionPersistent, final Boolean onboardingComplete, final String region, final String countryCode) {
        this.connected = connected;
        this.connectionPersistent = connectionPersistent;
        this.onboardingComplete = onboardingComplete;
        this.region = region;
        this.countryCode = countryCode;
    }

    public GatewayBackendAttributes() {
    }
}
