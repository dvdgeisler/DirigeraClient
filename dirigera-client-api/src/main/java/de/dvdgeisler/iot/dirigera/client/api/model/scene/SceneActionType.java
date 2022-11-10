package de.dvdgeisler.iot.dirigera.client.api.model.scene;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SceneActionType {
    @JsonProperty("device")
    DEVICE,
    @JsonProperty("deviceSet")
    DEVICE_SET
}
