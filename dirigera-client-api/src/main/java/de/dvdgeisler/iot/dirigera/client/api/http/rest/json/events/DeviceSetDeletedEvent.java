package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.EventType.DEVICE_SET_DELETED;

public class DeviceSetDeletedEvent extends DeviceSetEvent {
    public DeviceSetDeletedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final DeviceSet eventData) {
        super(id, time, specversion, source, DEVICE_SET_DELETED, eventData);
    }

    public DeviceSetDeletedEvent() {
    }
}
