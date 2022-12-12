package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        use = JsonTypeInfo.Id.NAME,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "pong", value = LastModifiedEvent.class),
        @JsonSubTypes.Type(name = "deviceConfigurationChanged", value = DeviceConfigurationChangedEvent.class),
        @JsonSubTypes.Type(name = "deviceStateChanged", value = DeviceStateChangedEvent.class),
        @JsonSubTypes.Type(name = "termination", value = TerminationEvent.class),
        @JsonSubTypes.Type(name = "deviceAdded", value = DeviceAddedEvent.class),
        @JsonSubTypes.Type(name = "deviceRemoved", value = DeviceRemovedEvent.class),
        @JsonSubTypes.Type(name = "deviceDiscovered", value = DeviceDiscoveredEvent.class),
        @JsonSubTypes.Type(name = "roomCreated", value = RoomCreatedEvent.class),
        @JsonSubTypes.Type(name = "roomDeleted", value = RoomDeletedEvent.class),
        @JsonSubTypes.Type(name = "roomUpdated", value = RoomUpdatedEvent.class),
        @JsonSubTypes.Type(name = "deviceSetCreated", value = DeviceSetCreatedEvent.class),
        @JsonSubTypes.Type(name = "deviceSetUpdated", value = DeviceSetUpdatedEvent.class),
        @JsonSubTypes.Type(name = "deviceSetDeleted", value = DeviceSetDeletedEvent.class),
        @JsonSubTypes.Type(name = "sceneCreated", value = SceneCreatedEvent.class),
        @JsonSubTypes.Type(name = "sceneDeleted", value = SceneDeletedEvent.class),
        @JsonSubTypes.Type(name = "sceneUpdated", value = SceneUpdatedEvent.class),
        @JsonSubTypes.Type(name = "sceneTriggered", value = SceneTriggeredEvent.class),
        @JsonSubTypes.Type(name = "sceneCompleted", value = SceneCompletedEvent.class),
        @JsonSubTypes.Type(name = "musicUpdated", value = MusicUpdatedEvent.class)})
public class Event<Data> {
    public String id;
    public LocalDateTime time;
    public String specversion;
    public String source;
    public EventType type;
    public Data data;

    public Event(final String id, final LocalDateTime time, final String specversion, final String source, final EventType type, final Data data) {
        this.id = id;
        this.time = time;
        this.specversion = specversion;
        this.source = source;
        this.type = type;
        this.data = data;
    }

    public Event() {
    }
}
