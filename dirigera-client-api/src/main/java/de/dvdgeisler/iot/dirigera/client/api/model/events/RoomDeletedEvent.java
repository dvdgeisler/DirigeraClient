package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.time.LocalDateTime;

public class RoomDeletedEvent extends Event<Room> {
    public RoomDeletedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final Room eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public RoomDeletedEvent() {
    }
}
