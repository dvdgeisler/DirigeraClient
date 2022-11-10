package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SceneTriggerType {
    @JsonProperty("app")
    APPLICATION,
    @JsonProperty("sunriseSunset")
    SUNRISE_SUNSET,
    @JsonProperty("time")
    TIME,
    @JsonProperty("motionSensor")
    MOTION_SENSOR,
    @JsonProperty("controller")
    CONTROLLER,
    @JsonProperty("duration")
    DURATION,
    @JsonProperty("other")
    OTHER
}
