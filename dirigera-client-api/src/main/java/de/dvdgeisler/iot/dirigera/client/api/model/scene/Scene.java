package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;

import java.time.LocalDateTime;

public class Scene extends Identifier {
    @JsonUnwrapped
    public SceneAttributes attributes;
    public LocalDateTime createdAt;
    public LocalDateTime lastCompleted;
    public LocalDateTime lastTriggered;
    public Integer undoAllowedDuration;
    public LocalDateTime lastUndo;

    public Scene() {
    }

    public Scene(final String id, final SceneAttributes attributes, final LocalDateTime createdAt, final LocalDateTime lastCompleted, final LocalDateTime lastTriggered, final Integer undoAllowedDuration, final LocalDateTime lastUndo) {
        super(id);
        this.attributes = attributes;
        this.createdAt = createdAt;
        this.lastCompleted = lastCompleted;
        this.lastTriggered = lastTriggered;
        this.undoAllowedDuration = undoAllowedDuration;
        this.lastUndo = lastUndo;
    }
}
