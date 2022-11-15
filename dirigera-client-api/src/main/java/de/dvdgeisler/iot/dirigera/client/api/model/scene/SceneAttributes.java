package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SceneAttributes {
    public SceneInfo info;
    public List<SceneTrigger> triggers;
    public List<SceneAction> actions;

    public SceneAttributes(final SceneInfo info, final List<SceneTrigger> triggers, final List<SceneAction> actions) {
        this.info = info;
        this.triggers = triggers;
        this.actions = actions;
    }

    public SceneAttributes() {
    }
}
