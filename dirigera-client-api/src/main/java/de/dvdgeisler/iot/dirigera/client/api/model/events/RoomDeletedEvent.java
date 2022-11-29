package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.events.EventType.ROOM_DELETED;

public class RoomDeletedEvent extends RoomEvent {
    public RoomDeletedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final Room eventData) {
        super(id, time, specversion, source, ROOM_DELETED, eventData);
    }

    public RoomDeletedEvent() {
    }
}
