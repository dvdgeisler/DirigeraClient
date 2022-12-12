package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

public class SceneEndTriggerDuration extends SceneEndTrigger {
    public SceneEndTriggerDurationTrigger trigger;

    public SceneEndTriggerDuration(final Boolean disabled, final SceneEndTriggerDurationTrigger trigger) {
        super(SceneTriggerType.DURATION, disabled);
        this.trigger = trigger;
    }

    public SceneEndTriggerDuration() {
    }
}
