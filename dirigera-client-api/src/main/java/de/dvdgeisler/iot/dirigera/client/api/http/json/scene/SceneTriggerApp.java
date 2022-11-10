package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.scene.SceneTriggerType.APPLICATION;

public class SceneTriggerApp extends SceneTrigger {
    public LocalDateTime triggeredAt;

    public SceneTriggerApp() {
    }

    public SceneTriggerApp(final String id, final Boolean disabled, final SceneEndTrigger endTrigger, final LocalDateTime triggeredAt) {
        super(id, APPLICATION, disabled, endTrigger);
        this.triggeredAt = triggeredAt;
    }
}
