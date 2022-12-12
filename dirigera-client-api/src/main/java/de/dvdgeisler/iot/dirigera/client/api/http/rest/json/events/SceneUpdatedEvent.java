package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene.Scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.EventType.SCENE_UPDATED;

public class SceneUpdatedEvent extends SceneEvent {
    public SceneUpdatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final Scene eventData) {
        super(id, time, specversion, source, SCENE_UPDATED, eventData);
    }

    public SceneUpdatedEvent() {
    }
}
