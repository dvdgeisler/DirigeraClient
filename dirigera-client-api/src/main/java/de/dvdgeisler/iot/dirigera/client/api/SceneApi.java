package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public class SceneApi {
    private final ClientApi clientApi;

    public SceneApi(final ClientApi clientApi) {
        this.clientApi = clientApi;
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
                .flatMap(id -> this.clientApi.scene.getScene(id.id));
    }

    public Mono<Void> delete(final Scene scene) {
        return this.clientApi.scene.deleteScene(scene.id);
    }

    public Mono<Scene> update(final Scene scene, final String name, final String icon) {
        return this.clientApi.scene.updateScene(scene.id, new SceneAttributes(new SceneInfo(name, icon), List.of(), List.of()))
                .thenReturn(scene)
                .flatMap(this::refresh);
    }
}
