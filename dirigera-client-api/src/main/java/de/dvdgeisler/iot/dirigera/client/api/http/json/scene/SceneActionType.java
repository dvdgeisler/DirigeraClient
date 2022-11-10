package de.dvdgeisler.iot.dirigera.client.api.http.json.scene;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SceneActionType {
    @JsonProperty("device")
    DEVICE,
    @JsonProperty("deviceSet")
    DEVICE_SET
}
