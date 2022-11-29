package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;

public class DeviceSetUpdatedEvent extends Event<DeviceSet> {
    public DeviceSetUpdatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final DeviceSet eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public DeviceSetUpdatedEvent() {
    }
}
