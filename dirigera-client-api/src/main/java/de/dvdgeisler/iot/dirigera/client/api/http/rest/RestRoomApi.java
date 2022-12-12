package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.RoomAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.List;

@Component
public class RestRoomApi extends AbstractRestApi {
    private final static Logger log = LoggerFactory.getLogger(RestRoomApi.class);

    private final RestOAuthApi oauth;

    public RestRoomApi(
            final RestApiDiscovery discovery,
            final RestOAuthApi oauth) throws SSLException {
        super(discovery, "rooms/");
        this.oauth = oauth;
    }

    public Mono<Identifier> createRoom(final String name, final String icon, final String color) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .post()
                        .uri(UriBuilder::build)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters
                                .fromFormData("name", name)
                                .with("icon", icon)
                                .with("color", color))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Identifier.class));
    }


    public Mono<Void> deleteRoom(final String id) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .delete()
                        .uri(uri -> uri.path("{id}").build(id))
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }

    public Mono<List<Room>> rooms() {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .get()
                        .uri(UriBuilder::build)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(new ParameterizedTypeReference<>() {
                        }));
    }

    public Mono<Room> getRoom(final String id) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .get()
                        .uri(uri -> uri.path("{id}").build(id))
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Room.class));
    }

    public Mono<Void> updateRoom(final String id, final RoomAttributes attributes) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .put()
                        .uri(uri -> uri.path("{id}").build(id))
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(attributes)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }
}
