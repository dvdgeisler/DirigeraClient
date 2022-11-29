package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.events.EventType.DEVICE_SET_CREATED;

public class DeviceSetCreatedEvent extends DeviceSetEvent {
    public DeviceSetCreatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final DeviceSet eventData) {
        super(id, time, specversion, source, DEVICE_SET_CREATED, eventData);
    }

    public DeviceSetCreatedEvent() {
    }
}
