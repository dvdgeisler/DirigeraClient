package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import java.util.List;

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
