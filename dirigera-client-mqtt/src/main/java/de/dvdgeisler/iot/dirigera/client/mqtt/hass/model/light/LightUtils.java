package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LightUtils {

    public static Integer kelvinToMireds(final Integer kelvin) {
        return Optional.ofNullable(kelvin)
                .filter(k -> k > 0)
                .map(k -> 1_000_000 / k)
                .orElse(null);
    }

    public static Integer miredsToKelvin(final Integer kelvin) {
        return Optional.ofNullable(kelvin)
                .filter(k -> k > 0)
                .map(k -> 1_000_000 / k)
                .orElse(null);
    }

    public static Integer getMaxTemperatureMireds(final LightDevice device) {
        return Optional.ofNullable(getMaxTemperatureKelvin(device))
                .map(LightUtils::kelvinToMireds)
                .orElse(null);
    }

    public static Integer getMaxTemperatureKelvin(final LightDevice device) {
        return Optional.ofNullable(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.temperatureMax)
                .orElse(null);
    }

    public static Integer getMinTemperatureMireds(final LightDevice device) {
        return Optional.ofNullable(getTemperatureMireds(device))
                .map(LightUtils::miredsToKelvin)
                .orElse(null);
    }

    public static Integer getMinTemperatureKelvin(final LightDevice device) {
        return Optional.ofNullable(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.temperatureMin)
                .orElse(null);
    }

    public static Integer getTemperatureMireds(final LightDevice device) {
        return Optional.ofNullable(getTemperatureKelvin(device))
                .map(LightUtils::kelvinToMireds)
                .orElse(null);
    }

    public static Integer getTemperatureKelvin(final LightDevice device) {
        return Optional.ofNullable(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.temperature)
                .orElse(null);
    }

    public static Integer getLightLevel(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.lightLevel)
                .orElse(null);
    }

    public static Float getHue(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.hue)
                .orElse(null);
    }

    public static Float getSaturation(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.saturation)
                .orElse(null);
    }

    public static List<LightColorMode> colorModes(final LightDevice device) {
        final List<LightColorMode> colorModes;

        colorModes = new ArrayList<>();

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("colorHue"))
                .filter(d->d.contains("colorSaturation"))
                .isPresent())
            colorModes.add(LightColorMode.HS);

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("colorTemperature"))
                .isPresent())
            colorModes.add(LightColorMode.COLOR_TEMP);

        if(!colorModes.isEmpty())
            return colorModes;

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("lightLevel"))
                .isPresent())
            colorModes.add(LightColorMode.BRIGHTNESS);

        if(!colorModes.isEmpty())
            return colorModes;

        if(Optional.of(device)
                .map(d -> d.capabilities)
                .map(d->d.canReceive)
                .filter(d->d.contains("isOn"))
                .isPresent())
            colorModes.add(LightColorMode.ONOFF);

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
                            case COLOR -> LightColorMode.HS;
                            case TEMPERATURE -> LightColorMode.COLOR_TEMP;
                        })
                .orElseGet(() ->
                        Optional.of(device)
                                .map(d -> d.capabilities)
                                .map(d -> d.canReceive)
                                .map(r -> {
                                    if (r.contains("lightLevel"))
                                        return LightColorMode.BRIGHTNESS;
                                    if (r.contains("isOn"))
                                        return LightColorMode.ONOFF;
                                    else return null;
                                })
                                .orElse(LightColorMode.UNKNOWN));
    }

    public static DeviceState getState(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.isOn)
                .filter(d -> d)
                .map(d -> DeviceState.ON)
                .orElse(DeviceState.OFF);
    }
}
