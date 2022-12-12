package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceType {
    @JsonProperty("gateway")
    GATEWAY,
    @JsonProperty("light")
    LIGHT,
    @JsonProperty("lightController")
    LIGHT_CONTROLLER,
    @JsonProperty("soundController")
    SOUND_CONTROLLER,
    @JsonProperty("motionSensor")
    MOTION_SENSOR,
    @JsonProperty("shortcutController")
    SHORTCUT_CONTROLLER,
    @JsonProperty("waterController")
    WATER_CONTROLLER,
    @JsonProperty("waterValve")
    WATER_VALVE,
    @JsonProperty("waterSensor")
    WATER_SENSOR,
    @JsonProperty("openCloseSensor")
    OPEN_CLOSE_SENSOR,
    @JsonProperty("outlet")
    OUTLET,
    @JsonProperty("repeater")
    REPEATER,
    @JsonProperty("blinds")
    BLINDS,
    @JsonProperty("blindsController")
    BLINDS_CONTROLLER,
    @JsonProperty("speaker")
    SPEAKER,
    @JsonProperty("lock")
    LOCK,
    @JsonProperty("airPurifier")
    AIR_PURIFIER,
    @JsonProperty("environmentSensor")
    ENVIRONMENT_SENSOR,
    @JsonProperty("notConfigured")
    NOT_CONFIGURED,
    @JsonProperty("other")
    OTHER;
}
