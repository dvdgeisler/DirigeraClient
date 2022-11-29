package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.time.LocalDateTime;

public class RoomCreatedEvent extends Event<Room> {
    public RoomCreatedEvent() {
    }

    public RoomCreatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final Room eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }
}
