package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;

import java.util.Optional;

public class MotionSensorUtils {

    public static DeviceState getState(final MotionSensorDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.isOn)
                .filter(d -> d)
                .map(d -> DeviceState.ON)
                .orElse(DeviceState.OFF);
    }
}
