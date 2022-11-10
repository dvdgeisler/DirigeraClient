package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SceneEndTriggerDuration.class, name = "duration"),
})
public class SceneEndTrigger {
    public SceneTriggerType type;
    public Boolean disabled;

    public SceneEndTrigger() {
    }

    public SceneEndTrigger(final SceneTriggerType type, final Boolean disabled) {
        this.type = type;
        this.disabled = disabled;
    }
}
