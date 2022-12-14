package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.ClientDeviceSetApi;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSetAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.events.DeviceSetEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DeviceSetApi {
    private final ClientDeviceSetApi deviceSetApi;
    private final ClientApi clientApi;
    private final WebSocketApi webSocketApi;

    public DeviceSetApi(final ClientDeviceSetApi deviceSetApi, final ClientApi clientApi, final WebSocketApi webSocketApi) {
        this.deviceSetApi = deviceSetApi;
        this.clientApi = clientApi;
        this.webSocketApi = webSocketApi;
    }

    Mono<List<DeviceSet>> all() {
        return this.clientApi.home().map(home -> home.deviceSets);
    }

    Mono<DeviceSet> refresh(final DeviceSet deviceSet) {
        return this.all()
                .flatMapMany(Flux::fromIterable)
                .filter(ds -> Objects.equals(ds.id, deviceSet.id))
                .singleOrEmpty();
    }

    Mono<DeviceSet> create(final String name, final String icon) {
        return this.deviceSetApi.createDeviceSet(name, icon)
                .flatMap(id -> this.all()
                        .flatMapMany(Flux::fromIterable)
                        .filter(ds -> Objects.equals(ds.id, id.id))
                        .singleOrEmpty());
    }

    Mono<Void> delete(final DeviceSet deviceSet) {
        return this.deviceSetApi.deleteDeviceSet(deviceSet.id);
    }

    Mono<DeviceSet> update(final DeviceSet deviceSet, final String name, final String icon) {
        return this.deviceSetApi.updateDeviceSet(deviceSet.id, new DeviceSetAttributes(name, icon))
                .flatMap(v -> this.refresh(deviceSet));
    }

    public void websocket(Consumer<DeviceSetEvent> consumer) {
        this.webSocketApi.addListener(consumer, DeviceSetEvent.class);
    }
}
