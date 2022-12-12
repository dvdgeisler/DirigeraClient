package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.Music;

import java.time.LocalDateTime;

public class MusicUpdatedEvent extends Event<Music> {
    public MusicUpdatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final Music eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public MusicUpdatedEvent() {
    }
}
