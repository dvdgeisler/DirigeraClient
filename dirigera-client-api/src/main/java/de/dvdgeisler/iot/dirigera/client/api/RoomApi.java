package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.RestApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.RoomAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.DeviceSetEvent;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

public class RoomApi {
    private final RestApi clientApi;
    private final WebSocketApi webSocketApi;

    public RoomApi(final RestApi clientApi, final WebSocketApi webSocketApi) {
        this.clientApi = clientApi;
        this.webSocketApi = webSocketApi;
    }

    public Mono<List<Room>> all() {
        return this.clientApi.room.rooms();
    }

    public Mono<Room> refresh(final Room room) {
        return this.clientApi.room.getRoom(room.id);
    }

    public Mono<Room> create(final String name, final String icon, final String color) {
        return this.clientApi.room.createRoom(name, icon, color)
                .flatMap(id -> this.clientApi.room.getRoom(id.id));
    }

    public Mono<Void> delete(final Room room) {
        return this.clientApi.room.deleteRoom(room.id);
    }

    public Mono<Room> update(final Room room, final String name, final String icon, final String color) {
        return this.clientApi.room.updateRoom(room.id, new RoomAttributes(name, icon, color))
                .thenReturn(room)
                .flatMap(this::refresh);
    }

    public void websocket(Consumer<DeviceSetEvent> consumer) {
        this.webSocketApi.addListener(consumer, DeviceSetEvent.class);
    }
}
