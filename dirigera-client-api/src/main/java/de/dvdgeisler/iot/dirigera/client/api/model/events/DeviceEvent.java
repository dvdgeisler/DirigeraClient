package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;

import java.time.LocalDateTime;

public class DeviceEvent extends Event<Device> {
    public DeviceEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType type, final Device device) {
        super(id, time, specversion, source, type, device);
    }

    public DeviceEvent() {
    }
}
