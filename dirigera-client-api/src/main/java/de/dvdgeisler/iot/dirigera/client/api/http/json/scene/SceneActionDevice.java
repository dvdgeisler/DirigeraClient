package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import java.util.Map;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.scene.SceneActionType.DEVICE;

public class SceneActionDevice extends SceneAction {
    public String deviceId;
    public Map<String, Object> attributes;

    public SceneActionDevice(final String id, final String deviceId, final Map<String, Object> attributes) {
        super(id, DEVICE);
        this.deviceId = deviceId;
        this.attributes = attributes;
    }

    public SceneActionDevice() {
    }
}
