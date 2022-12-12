package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;

import java.time.LocalDateTime;

public class RoomEvent extends Event<Room> {
    public RoomEvent() {
    }

    public RoomEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType type, final Room eventData) {
        super(id, time, specversion, source, type, eventData);
    }
}
