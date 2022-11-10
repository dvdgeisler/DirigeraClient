package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.scene.SceneTriggerType.DURATION;

public class SceneEndTriggerDuration extends SceneEndTrigger {
    public SceneEndTriggerDurationTrigger trigger;

    public SceneEndTriggerDuration(final Boolean disabled, final SceneEndTriggerDurationTrigger trigger) {
        super(DURATION, disabled);
        this.trigger = trigger;
    }

    public SceneEndTriggerDuration() {
    }
}
