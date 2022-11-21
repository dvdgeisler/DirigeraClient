package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LightColorMode {
    @JsonProperty("xy")
    XY,
    @JsonProperty("white")
    WHITE,
    @JsonProperty("rgbww")
    RGBWW,
    @JsonProperty("rgbw")
    RGBW,
    @JsonProperty("rgb")
    RGB,
    @JsonProperty("hs")
    HS,
    @JsonProperty("color_temp")
    COLOR_TEMP,
    @JsonProperty("brightness")
    BRIGHTNESS,
    @JsonProperty("onoff")
    ONOFF,
    UNKNOWN;

}
