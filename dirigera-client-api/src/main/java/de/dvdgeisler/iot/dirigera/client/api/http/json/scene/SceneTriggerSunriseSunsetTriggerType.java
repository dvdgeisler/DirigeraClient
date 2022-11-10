package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SceneTriggerSunriseSunsetTriggerType {
    @JsonProperty("sunrise")
    SUNRISE,
    @JsonProperty("sunset")
    SUNSET
}
