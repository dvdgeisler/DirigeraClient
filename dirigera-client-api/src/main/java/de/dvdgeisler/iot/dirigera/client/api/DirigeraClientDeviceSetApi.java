package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.model.Identifier;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSetAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class DirigeraClientDeviceSetApi extends AbstractApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientDeviceSetApi.class);

    public DirigeraClientDeviceSetApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore) throws SSLException {
        super(String.format("https://%s:%d/v1/device-set/", hostname, port), tokenStore);
    }

    public Mono<Identifier> createDeviceSet(final String name, final String icon) {
        return this.webClient
                .post()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("icon", icon))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Identifier.class);
    }

    public Mono<Void> deleteDeviceSet(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateDeviceSet(final String id, final DeviceSetAttributes attributes) {
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
