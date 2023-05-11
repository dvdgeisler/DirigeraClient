package de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AirPurifierFanMode {
    @JsonProperty("low")
    LOW,
    @JsonProperty("medium")
    MEDIUM,
    @JsonProperty("high")
    HIGH,
    @JsonProperty("auto")
    AUTO,
    @JsonProperty("off")
    OFF,
    @JsonProperty("on")
    ON
}
