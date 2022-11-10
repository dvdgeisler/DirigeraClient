package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LightStartupMode {
    @JsonProperty("startOn")
    START_ON,
    @JsonProperty("startOff")
    START_OFF
}
