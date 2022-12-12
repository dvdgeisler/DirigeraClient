package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.scene;

import java.util.Map;

public class SceneActionDevice extends SceneAction {
    public String deviceId;
    public Map<String, Object> attributes;

    public SceneActionDevice(final String id, final String deviceId, final Map<String, Object> attributes) {
        super(id, SceneActionType.DEVICE);
        this.deviceId = deviceId;
        this.attributes = attributes;
    }

    public SceneActionDevice() {
    }
}
