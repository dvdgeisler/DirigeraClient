package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerType.TIME;

public class SceneTriggerTime extends SceneTrigger {
    public SceneTriggerTimeTrigger trigger;
    public LocalDateTime nextTriggerAt;
    public LocalDateTime triggeredAt;
    public SceneEndTrigger endTrigger;

    public SceneTriggerTime(final String id, final SceneTriggerType type, final Boolean disabled, final SceneEndTrigger endTrigger, final SceneTriggerTimeTrigger trigger, final LocalDateTime nextTriggerAt, final LocalDateTime triggeredAt) {
        super(id, TIME, disabled);
        this.trigger = trigger;
        this.nextTriggerAt = nextTriggerAt;
        this.triggeredAt = triggeredAt;
        this.endTrigger = endTrigger;
    }

    public SceneTriggerTime() {
    }
}
