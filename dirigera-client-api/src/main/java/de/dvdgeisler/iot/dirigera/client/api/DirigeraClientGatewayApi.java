package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayEnvironment;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayPersistentMode;
import de.dvdgeisler.iot.dirigera.client.api.model.device.gateway.GatewayStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class DirigeraClientGatewayApi extends AbstractApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientGatewayApi.class);

    public DirigeraClientGatewayApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore) throws SSLException {
        super(String.format("https://%s:%d/v1/hub/", hostname, port), tokenStore);
    }

    public Mono<GatewayStatus> status() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("status").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(GatewayStatus.class);
    }

    public Mono<Void> checkFirmwareUpdate() {
        return this.webClient
                .put()
                .uri(uri -> uri.path("ota/check").build())
                .headers(this.tokenStore::setBearerAuth)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }
    public Mono<Void> installFirmwareUpdate() {
        return this.webClient
                .put()
                .uri(uri -> uri.path("ota/update").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }
    /**
     * TODO: results in 404 error, might not supported in release mode
     */
    public Mono<Void> setFirmwareEnvironment(final GatewayEnvironment environment) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("ota/environment").build())
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(environment)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> setPersistentMode(final GatewayPersistentMode mode) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("cloud-integration").build())
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mode)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }
}
