package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SceneTriggerApp.class, name = "app"),
        @JsonSubTypes.Type(value = SceneTriggerSunriseSunset.class, name = "sunriseSunset"),
        @JsonSubTypes.Type(value = SceneTriggerTime.class, name = "time"),
        @JsonSubTypes.Type(value = SceneTriggerController.class, name = "controller"),
})
public class SceneTrigger extends Identifier {
    public SceneTriggerType type;
    public Boolean disabled;
    public LocalDateTime triggeredAt;
    public SceneEndTrigger endTrigger;

    public SceneTrigger() {
    }

    public SceneTrigger(final String id, final SceneTriggerType type, final Boolean disabled, final LocalDateTime triggeredAt, final SceneEndTrigger endTrigger) {
        super(id);
        this.type = type;
        this.disabled = disabled;
        this.triggeredAt = triggeredAt;
        this.endTrigger = endTrigger;
    }
}
