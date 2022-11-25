package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerType.CONTROLLER;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneTriggerController extends SceneTrigger {
    public SceneTriggerControllerTrigger trigger;

    public SceneTriggerController() {
    }

    public SceneTriggerController(final String id, final Boolean disabled, final LocalDateTime triggeredAt, final SceneEndTrigger endTrigger, final SceneTriggerControllerTrigger trigger) {
        super(id, CONTROLLER, disabled, triggeredAt, endTrigger);
        this.trigger = trigger;
    }
}
