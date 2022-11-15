package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GatewayUserConsentValue {
    @JsonProperty("enabled")
    ENABLED,
    @JsonProperty("disabled")
    DISABLED
}
