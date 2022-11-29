package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.events.SceneEvent;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneInfo;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTrigger;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

public class SceneApi {
    private final ClientApi clientApi;
    private final WebSocketApi webSocketApi;

    public SceneApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        this.clientApi = clientApi;
        this.webSocketApi = webSocketApi;
    }

    public Mono<List<Scene>> all() {
        return this.clientApi.scene.scenes();
    }

    public Mono<Scene> refresh(final Scene scene) {
        return this.clientApi.scene.getScene(scene.id);
    }

    public Mono<Scene> create(final String name, final String icon) {
        return this.clientApi.scene.createScene(new SceneAttributes(
                        new SceneInfo(name, icon), List.of(), List.of()))
                .flatMap(id -> this.clientApi.scene.getScene(id.id).retry(10));
    }

    public Mono<Void> delete(final Scene scene) {
        return this.clientApi.scene.deleteScene(scene.id);
    }

    public Mono<Scene> update(final Scene scene, final String name, final String icon) {
        return this.clientApi.scene.updateScene(scene.id, new SceneAttributes(new SceneInfo(name, icon), null, null))
                .thenReturn(scene)
                .flatMap(this::refresh);
    }

    public Mono<Scene> setTrigger(final Scene scene, final List<SceneTrigger> triggers) {
        return this.clientApi.scene.updateScene(scene.id, new SceneAttributes(scene.attributes.info, triggers, null))
                .thenReturn(scene)
                .flatMap(this::refresh);
    }

    public void websocket(Consumer<SceneEvent> consumer) {
        this.webSocketApi.addListener(consumer, SceneEvent.class);
    }
}
