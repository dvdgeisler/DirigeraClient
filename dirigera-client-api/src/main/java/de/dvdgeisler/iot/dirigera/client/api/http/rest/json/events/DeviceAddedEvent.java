package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;

public class DeviceAddedEvent<_Device extends Device> extends DeviceEvent<_Device> {
    public DeviceAddedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final _Device eventData) {
        super(id, time, specversion, source, EventType.DEVICE_ADDED, eventData);
    }

    public DeviceAddedEvent() {
    }
}
