package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;

import java.time.LocalDateTime;

public class DeviceEvent<_Device extends Device> extends Event<_Device> {
    public DeviceEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType type, final _Device device) {
        super(id, time, specversion, source, type, device);
    }

    public DeviceEvent() {
    }
}
