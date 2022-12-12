package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSetAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class ClientDeviceSetApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientDeviceSetApi.class);

    private final ClientOAuthApi oauth;

    public ClientDeviceSetApi(
            final RestApiDiscovery discovery,
            final ClientOAuthApi oauth) throws SSLException {
        super(discovery, "device-set/");
        this.oauth = oauth;
    }

    public Mono<Identifier> createDeviceSet(final String name, final String icon) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .post()
                        .uri(UriBuilder::build)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters
                                .fromFormData("name", name)
                                .with("icon", icon))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Identifier.class));
    }

    public Mono<Void> deleteDeviceSet(final String id) {
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

    public Mono<Void> updateDeviceSet(final String id, final DeviceSetAttributes attributes) {
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
