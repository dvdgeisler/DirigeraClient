package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene.Scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.EventType.SCENE_DELETED;

public class SceneDeletedEvent extends SceneEvent {
    public SceneDeletedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final Scene eventData) {
        super(id, time, specversion, source, SCENE_DELETED, eventData);
    }

    public SceneDeletedEvent() {
    }
}
