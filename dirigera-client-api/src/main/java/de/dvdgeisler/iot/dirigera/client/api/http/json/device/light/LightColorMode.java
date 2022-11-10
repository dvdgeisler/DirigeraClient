package de.dvdgeisler.iot.dirigera.client.api.http.json.device.light;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LightColorMode {
    @JsonProperty("color")
    COLOR,
    @JsonProperty("temperature")
    TEMPERATURE
}
