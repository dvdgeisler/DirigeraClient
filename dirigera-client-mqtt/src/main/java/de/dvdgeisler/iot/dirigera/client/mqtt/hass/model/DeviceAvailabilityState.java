package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;

public enum DeviceAvailabilityState {
    ONLINE,
    OFFLINE;

    public static DeviceAvailabilityState get(final Device device) {
        if (device.isReachable)
            return ONLINE;
        return OFFLINE;
    }
}
