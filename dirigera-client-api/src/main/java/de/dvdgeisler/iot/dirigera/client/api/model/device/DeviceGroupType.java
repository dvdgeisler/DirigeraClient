package de.dvdgeisler.iot.dirigera.client.api.model.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DeviceGroupType {
    @JsonProperty("audio_group")
    AUDIO_GROUP,
    @JsonProperty("set")
    DEVICE_SET,
    @JsonProperty("room")
    ROOM
}
