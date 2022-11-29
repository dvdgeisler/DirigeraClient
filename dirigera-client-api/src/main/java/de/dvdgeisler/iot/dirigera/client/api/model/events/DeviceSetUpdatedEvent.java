package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.events.EventType.DEVICE_SET_UPDATED;

public class DeviceSetUpdatedEvent extends DeviceSetEvent {
    public DeviceSetUpdatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final DeviceSet eventData) {
        super(id, time, specversion, source, DEVICE_SET_UPDATED, eventData);
    }

    public DeviceSetUpdatedEvent() {
    }
}
