package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GatewayUserConsentName {
    @JsonProperty("analytics")
    ANALYTICS,
    @JsonProperty("diagnostics")
    DIAGNOSTICS
}
