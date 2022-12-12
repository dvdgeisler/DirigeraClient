package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneTriggerSunriseSunset extends SceneTrigger {
    public SceneTriggerSunriseSunsetTrigger trigger;
    public LocalDateTime nextTriggerAt;

    public SceneTriggerSunriseSunset(final String id, final Boolean disabled, final SceneEndTrigger endTrigger, final SceneTriggerSunriseSunsetTrigger trigger, final LocalDateTime nextTriggerAt, final LocalDateTime triggeredAt) {
        super(id, SceneTriggerType.SUNRISE_SUNSET, disabled, triggeredAt, endTrigger);
        this.trigger = trigger;
        this.nextTriggerAt = nextTriggerAt;
    }

    public SceneTriggerSunriseSunset() {
    }
}
