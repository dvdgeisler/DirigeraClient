package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import java.time.LocalDateTime;

public class SceneTriggerTime extends SceneTrigger {
    public SceneTriggerTimeTrigger trigger;
    public LocalDateTime nextTriggerAt;

    public SceneTriggerTime(final String id, final Boolean disabled, final SceneEndTrigger endTrigger, final SceneTriggerTimeTrigger trigger, final LocalDateTime nextTriggerAt, final LocalDateTime triggeredAt) {
        super(id, SceneTriggerType.TIME, disabled, triggeredAt, endTrigger);
        this.trigger = trigger;
        this.nextTriggerAt = nextTriggerAt;
    }

    public SceneTriggerTime() {
    }
}
