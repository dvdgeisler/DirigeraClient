package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.RoomAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class DirigeraClientRoomApi extends AbstractApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientRoomApi.class);

    public DirigeraClientRoomApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore) throws SSLException {
        super(String.format("https://%s:%d/v1/rooms/", hostname, port), tokenStore);
    }

    public Mono<Identifier> createRoom(final String name, final String icon, final String color) {
        return this.webClient
                .post()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("icon", icon)
                        .with("color", color))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Identifier.class);
    }


    public Mono<Void> deleteRoom(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<List<Room>> rooms() {
        return this.webClient
                .get()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<List<Room>>() {
                });
    }

    public Mono<Void> updateRoom(final String id, final RoomAttributes attributes) {
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
