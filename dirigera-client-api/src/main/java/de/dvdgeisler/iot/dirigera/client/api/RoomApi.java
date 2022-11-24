package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.RoomAttributes;
import reactor.core.publisher.Mono;

import java.util.List;

public class RoomApi {
    private final ClientApi clientApi;

    public RoomApi(final ClientApi clientApi) {
        this.clientApi = clientApi;
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
}
