package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SceneTriggerApp.class, name = "app"),
        @JsonSubTypes.Type(value = SceneTriggerSunriseSunset.class, name = "sunriseSunset"),
        @JsonSubTypes.Type(value = SceneTriggerTime.class, name = "time"),
})
public class SceneTrigger extends Identifier {
    public SceneTriggerType type;
    public Boolean disabled;

    public SceneTrigger() {
    }

    public SceneTrigger(final String id, final SceneTriggerType type, final Boolean disabled) {
        super(id);
        this.type = type;
        this.disabled = disabled;
    }
}
