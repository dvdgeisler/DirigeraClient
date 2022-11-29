package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;

public class DeviceSetCreatedEvent extends Event<DeviceSet> {
    public DeviceSetCreatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final DeviceSet eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public DeviceSetCreatedEvent() {
    }
}
