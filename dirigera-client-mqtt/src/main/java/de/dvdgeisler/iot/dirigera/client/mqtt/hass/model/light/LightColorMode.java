package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static List<LightColorMode> colorModes(final LightDevice device) {
        final List<LightColorMode> colorModes;

        colorModes = new ArrayList<>();

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("colorHue"))
                .filter(d->d.contains("colorSaturation"))
                .isPresent())
            colorModes.add(HS);

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("colorTemperature"))
                .isPresent())
            colorModes.add(COLOR_TEMP);

        if(!colorModes.isEmpty())
            return colorModes;

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("lightLevel"))
                .isPresent())
            colorModes.add(BRIGHTNESS);

        if(!colorModes.isEmpty())
            return colorModes;

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("isOn"))
                .isPresent())
            colorModes.add(ONOFF);

        return colorModes;
    }

    public static LightColorMode colorMode(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.mode)
                .map(mode ->
                        switch (mode) {
                            case COLOR -> HS;
                            case TEMPERATURE -> COLOR_TEMP;
                        })
                .orElseGet(() ->
                        Optional.of(device)
                                .map(d -> d.capabilities)
                                .map(d -> d.canReceive)
                                .map(r -> {
                                    if (r.contains("lightLevel"))
                                        return BRIGHTNESS;
                                    if (r.contains("isOn"))
                                        return ONOFF;
                                    else return null;
                                })
                                .orElse(UNKNOWN));
    }

}
