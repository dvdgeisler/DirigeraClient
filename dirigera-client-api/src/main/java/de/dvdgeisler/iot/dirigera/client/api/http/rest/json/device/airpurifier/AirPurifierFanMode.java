package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.airpurifier;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AirPurifierFanMode {
    @JsonProperty("off")
    OFF,
    @JsonProperty("auto")
    AUTO,
    @JsonProperty("manual")
    MANUAL
}
