package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.events.EventType.DEVICE_SET_DELETED;

public class DeviceSetDeletedEvent extends DeviceSetEvent {
    public DeviceSetDeletedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final DeviceSet eventData) {
        super(id, time, specversion, source, DEVICE_SET_DELETED, eventData);
    }

    public DeviceSetDeletedEvent() {
    }
}
