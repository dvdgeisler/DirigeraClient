package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerType.APPLICATION;

public class SceneTriggerApp extends SceneTrigger {
    public LocalDateTime triggeredAt;

    public SceneTriggerApp() {
    }

    public SceneTriggerApp(final String id, final Boolean disabled, final LocalDateTime triggeredAt) {
        super(id, APPLICATION, disabled);
        this.triggeredAt = triggeredAt;
    }
}
