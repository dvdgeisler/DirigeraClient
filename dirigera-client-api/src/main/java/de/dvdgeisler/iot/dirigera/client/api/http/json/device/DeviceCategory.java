package de.dvdgeisler.iot.dirigera.client.api.http.json.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceCategory {
    @JsonProperty("gateway")
    GATEWAY,
    @JsonProperty("light")
    LIGHT,
    @JsonProperty("controller")
    CONTROLLER,
    @JsonProperty("sensor")
    SENSOR,
    @JsonProperty("outlet")
    OUTLET,
    @JsonProperty("blinds")
    BLINDS,
    @JsonProperty("airPurifier")
    AIR_PURIFIER,
    @JsonProperty("speaker")
    SPEAKER,
    @JsonProperty("repeater")
    REPEATER,
    @JsonProperty("lock")
    LOCK,
    @JsonProperty("thermostat")
    THERMOSTAT,
    @JsonProperty("musicService")
    MUSIC_SERVICE,
    @JsonProperty("other")
    OTHER;
}
