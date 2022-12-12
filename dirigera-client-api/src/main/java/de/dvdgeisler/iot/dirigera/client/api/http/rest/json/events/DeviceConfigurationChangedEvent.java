package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.EventType.DEVICE_STATE_CHANGED;

public class DeviceConfigurationChangedEvent<_Device extends Device> extends DeviceEvent<_Device> {
    public DeviceConfigurationChangedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final _Device eventData) {
        super(id, time, specversion, source, DEVICE_STATE_CHANGED, eventData);
    }

    public DeviceConfigurationChangedEvent() {
    }
}
