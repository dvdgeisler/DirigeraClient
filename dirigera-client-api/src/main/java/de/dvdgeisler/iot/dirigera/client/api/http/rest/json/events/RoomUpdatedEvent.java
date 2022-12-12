package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.EventType.ROOM_UPDATED;

public class RoomUpdatedEvent extends RoomEvent {
    public RoomUpdatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final Room eventData) {
        super(id, time, specversion, source, ROOM_UPDATED, eventData);
    }

    public RoomUpdatedEvent() {
    }
}
