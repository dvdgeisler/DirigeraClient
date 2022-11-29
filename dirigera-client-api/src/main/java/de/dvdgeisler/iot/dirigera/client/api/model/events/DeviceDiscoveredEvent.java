package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;

import java.time.LocalDateTime;

public class DeviceDiscoveredEvent extends Event<Device> {
    public DeviceDiscoveredEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final Device eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public DeviceDiscoveredEvent() {
    }
}
