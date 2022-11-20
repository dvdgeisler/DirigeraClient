package de.dvdgeisler.iot.dirigera.client.api.model.device.blinds;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BlindsState {
    @JsonProperty("up")
    UP,
    @JsonProperty("down")
    DOWN,
    @JsonProperty("stopped")
    STOPPED,
    TEST
}
