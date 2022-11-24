package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerType.TIME;

public class SceneTriggerTime extends SceneTrigger {
    public SceneTriggerTimeTrigger trigger;
    public LocalDateTime nextTriggerAt;

    public SceneTriggerTime(final String id, final Boolean disabled, final SceneEndTrigger endTrigger, final SceneTriggerTimeTrigger trigger, final LocalDateTime nextTriggerAt, final LocalDateTime triggeredAt) {
        super(id, TIME, disabled, triggeredAt, endTrigger);
        this.trigger = trigger;
        this.nextTriggerAt = nextTriggerAt;
    }

    public SceneTriggerTime() {
    }
}
