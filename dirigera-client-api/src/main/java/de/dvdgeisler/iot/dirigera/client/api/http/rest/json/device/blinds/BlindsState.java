package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blinds;

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
