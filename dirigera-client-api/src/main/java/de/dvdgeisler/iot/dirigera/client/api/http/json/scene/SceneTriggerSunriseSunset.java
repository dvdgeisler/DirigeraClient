package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.scene.SceneTriggerType.SUNRISE_SUNSET;

public class SceneTriggerSunriseSunset extends SceneTrigger {
    public SceneTriggerSunriseSunsetTrigger trigger;
    public LocalDateTime nextTriggerAt;
    public LocalDateTime triggeredAt;

    public SceneTriggerSunriseSunset(final String id, final Boolean disabled, final SceneEndTrigger endTrigger, final SceneTriggerSunriseSunsetTrigger trigger, final LocalDateTime nextTriggerAt, final LocalDateTime triggeredAt) {
        super(id, SUNRISE_SUNSET, disabled, endTrigger);
        this.trigger = trigger;
        this.nextTriggerAt = nextTriggerAt;
        this.triggeredAt = triggeredAt;
    }

    public SceneTriggerSunriseSunset() {
    }
}
