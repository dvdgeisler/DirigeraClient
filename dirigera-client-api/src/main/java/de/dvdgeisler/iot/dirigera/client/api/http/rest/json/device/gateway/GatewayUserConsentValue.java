package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum GatewayUserConsentValue {
    @JsonProperty("enabled")
    ENABLED,
    @JsonProperty("disabled")
    DISABLED
}
