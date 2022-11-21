package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.outlet;

import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;

import java.util.Optional;

public class OutletUtils {

    public static DeviceState getState(final OutletDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.isOn)
                .filter(d -> d)
                .map(d -> DeviceState.ON)
                .orElse(DeviceState.OFF);
    }
}
