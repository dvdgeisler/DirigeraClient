package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;

import java.time.LocalDateTime;

public class SceneEvent extends Event<Scene> {
    public SceneEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType type, final Scene eventData) {
        super(id, time, specversion, source, type, eventData);
    }

    public SceneEvent() {
    }
}
