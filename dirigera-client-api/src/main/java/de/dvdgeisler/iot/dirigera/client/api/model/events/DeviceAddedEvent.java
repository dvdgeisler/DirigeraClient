package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.events.EventType.DEVICE_ADDED;

public class DeviceAddedEvent<_Device extends Device> extends DeviceEvent<_Device> {
    public DeviceAddedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final _Device eventData) {
        super(id, time, specversion, source, DEVICE_ADDED, eventData);
    }

    public DeviceAddedEvent() {
    }
}
