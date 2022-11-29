package de.dvdgeisler.iot.dirigera.client.api.model.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventType {
    @JsonProperty("deviceStateChanged")
    DEVICE_STATE_CHANGED,
    @JsonProperty("ping")
    PING,
    @JsonProperty("pong")
    LAST_MODIFIED,
    @JsonProperty("termination")
    TERMINATION,
    @JsonProperty("deviceAdded")
    DEVICE_ADDED,
    @JsonProperty("deviceRemoved")
    DEVICE_REMOVED,
    @JsonProperty("deviceDiscovered")
    DEVICE_DISCOVERED,
    @JsonProperty("roomCreated")
    ROOM_CREATED,
    @JsonProperty("roomDeleted")
    ROOM_DELETED,
    @JsonProperty("roomUpdated")
    ROOM_UPDATED,
    @JsonProperty("deviceSetCreated")
    DEVICE_SET_CREATED,
    @JsonProperty("deviceSetUpdated")
    DEVICE_SET_UPDATED,
    @JsonProperty("deviceSetDeleted")
    DEVICE_SET_DELETED,
    @JsonProperty("sceneCreated")
    SCENE_CREATED,
    @JsonProperty("sceneDeleted")
    SCENE_DELETED,
    @JsonProperty("sceneUpdated")
    SCENE_UPDATED,
    @JsonProperty("sceneTriggered")
    SCENE_TRIGGERED,
    @JsonProperty("sceneCompleted")
    SCENE_COMPLETED,
    @JsonProperty("musicUpdated")
    MUSIC_UPDATED
}
