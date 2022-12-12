package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import java.time.LocalTime;
import java.util.List;

public class SceneTriggerTimeTrigger {
    public List<String> days;
    public LocalTime time;

    public SceneTriggerTimeTrigger() {
    }

    public SceneTriggerTimeTrigger(final List<String> days, final LocalTime time) {
        this.days = days;
        this.time = time;
    }
}
