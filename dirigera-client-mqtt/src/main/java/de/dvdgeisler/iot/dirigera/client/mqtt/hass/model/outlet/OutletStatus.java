package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.outlet;

import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;

public class OutletStatus {
    public DeviceState state;

    public OutletStatus(final DeviceState state) {
        this.state = state;
    }

    public OutletStatus() {
    }
}
