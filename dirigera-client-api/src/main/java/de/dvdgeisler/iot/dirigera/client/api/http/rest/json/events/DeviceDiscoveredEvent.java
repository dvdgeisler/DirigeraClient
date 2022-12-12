package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.EventType.DEVICE_DISCOVERED;

public class DeviceDiscoveredEvent<_Device extends Device> extends DeviceEvent<_Device> {
    public DeviceDiscoveredEvent(final String id, final LocalDateTime time, final String specversion, final String source, final _Device eventData) {
        super(id, time, specversion, source, DEVICE_DISCOVERED, eventData);
    }

    public DeviceDiscoveredEvent() {
    }
}
