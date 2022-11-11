package de.dvdgeisler.iot.dirigera.client.api.model.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceStartupMode {
    @JsonProperty("startOn")
    START_ON,
    @JsonProperty("startOff")
    START_OFF,
    @JsonProperty("startPrevious")
    START_PREVIOUS
}
