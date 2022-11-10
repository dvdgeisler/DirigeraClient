package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import java.util.List;

public class SceneTriggerSunriseSunsetTrigger {
    public List<String> days;
    public SceneTriggerSunriseSunsetTriggerType type;
    public Integer offset;

    public SceneTriggerSunriseSunsetTrigger() {
    }

    public SceneTriggerSunriseSunsetTrigger(final List<String> days, final SceneTriggerSunriseSunsetTriggerType type, final Integer offset) {
        this.days = days;
        this.type = type;
        this.offset = offset;
    }
}
