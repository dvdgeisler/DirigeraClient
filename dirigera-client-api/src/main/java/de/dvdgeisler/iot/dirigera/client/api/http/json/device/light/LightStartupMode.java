package de.dvdgeisler.iot.dirigera.client.api.http.json.device.light;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LightStartupMode {
    @JsonProperty("startOn")
    START_ON,
    @JsonProperty("startOff")
    START_OFF
}
