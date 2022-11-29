package de.dvdgeisler.iot.dirigera.client.api.model.events;

import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;

import java.time.LocalDateTime;

public class SceneCreatedEvent extends Event<Scene> {
    public SceneCreatedEvent(final String id, final LocalDateTime time, final String specversion, final String source, final EventType deviceStateChanged, final Scene eventData) {
        super(id, time, specversion, source, deviceStateChanged, eventData);
    }

    public SceneCreatedEvent() {
    }
}
