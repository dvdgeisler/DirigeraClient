package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerType.SUNRISE_SUNSET;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneTriggerSunriseSunset extends SceneTrigger {
    public SceneTriggerSunriseSunsetTrigger trigger;
    public LocalDateTime nextTriggerAt;

    public SceneTriggerSunriseSunset(final String id, final Boolean disabled, final SceneEndTrigger endTrigger, final SceneTriggerSunriseSunsetTrigger trigger, final LocalDateTime nextTriggerAt, final LocalDateTime triggeredAt) {
        super(id, SUNRISE_SUNSET, disabled, triggeredAt, endTrigger);
        this.trigger = trigger;
        this.nextTriggerAt = nextTriggerAt;
    }

    public SceneTriggerSunriseSunset() {
    }
}
