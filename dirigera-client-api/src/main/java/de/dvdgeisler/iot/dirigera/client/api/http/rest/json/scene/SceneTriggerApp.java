package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene.SceneTriggerType.APPLICATION;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneTriggerApp extends SceneTrigger {

    public SceneTriggerApp() {
    }

    public SceneTriggerApp(final String id, final Boolean disabled, final LocalDateTime triggeredAt, final SceneEndTrigger endTrigger) {
        super(id, APPLICATION, disabled, triggeredAt, endTrigger);
    }
}
