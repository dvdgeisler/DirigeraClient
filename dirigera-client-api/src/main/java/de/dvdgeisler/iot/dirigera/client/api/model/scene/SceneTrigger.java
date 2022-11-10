package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SceneTriggerApp.class, name = "app"),
        @JsonSubTypes.Type(value = SceneTriggerSunriseSunset.class, name = "sunriseSunset"),
})
public class SceneTrigger extends Identifier {
    public SceneTriggerType type;
    public Boolean disabled;
    public SceneEndTrigger endTrigger;

    public SceneTrigger() {
    }

    public SceneTrigger(final String id, final SceneTriggerType type, final Boolean disabled, final SceneEndTrigger endTrigger) {
        super(id);
        this.type = type;
        this.disabled = disabled;
        this.endTrigger = endTrigger;
    }
}
