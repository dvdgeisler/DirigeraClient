package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum DeviceState {
    ON,
    OFF;

    public static DeviceState state(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.isOn)
                .map(d -> d ? ON : OFF)
                .orElse(OFF);
    }
}
