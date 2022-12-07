package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.List;

@Component
public class ClientSceneApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientSceneApi.class);

    public ClientSceneApi(
            final GatewayDiscovery gatewayDiscovery,
            final TokenStore tokenStore) throws SSLException {
        super(gatewayDiscovery, "scenes/", tokenStore);
    }



    /**
     * TODO: fails if any trigger or action is set: "triggers[0]" does not match any of the allowed types, "actions[0]" does not match any of the allowed types
     */
    public Mono<Identifier> createScene(final SceneAttributes attributes) {
        return this.webClient
                .post()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Identifier.class);
    }

    public Mono<Void> deleteScene(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<List<Scene>> scenes() {
        return this.webClient
                .get()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<List<Scene>>() {
                });
    }

    public Mono<Scene> getScene(final String id) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Scene.class);
    }

    public Mono<Void> triggerScene(final String id) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("{id}/trigger").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> undoScene(final String id) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("{id}/trigger").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateScene(final String id, final SceneAttributes attributes) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

}
