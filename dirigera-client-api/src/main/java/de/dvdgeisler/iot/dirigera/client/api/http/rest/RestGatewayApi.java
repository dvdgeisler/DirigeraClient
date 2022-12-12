package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway.GatewayEnvironment;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway.GatewayPersistentMode;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway.GatewayStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class RestGatewayApi extends AbstractRestApi {
    private final static Logger log = LoggerFactory.getLogger(RestGatewayApi.class);

    private final RestOAuthApi oauth;

    public RestGatewayApi(
            final RestApiDiscovery discovery,
            final RestOAuthApi oauth) throws SSLException {
        super(discovery, "hub/");
        this.oauth = oauth;
    }

    public Mono<GatewayStatus> status() {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .get()
                        .uri(uri -> uri.path("status").build())
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(GatewayStatus.class));
    }

    public Mono<Void> checkFirmwareUpdate() {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .put()
                        .uri(uri -> uri.path("ota/check").build())
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }

    public Mono<Void> installFirmwareUpdate() {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .put()
                        .uri(uri -> uri.path("ota/update").build())
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }

    /**
     * TODO: results in 404 error, might not supported in release mode
     */
    public Mono<Void> setFirmwareEnvironment(final GatewayEnvironment environment) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .put()
                        .uri(uri -> uri.path("ota/environment").build())
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(environment)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }

    public Mono<Void> setPersistentMode(final GatewayPersistentMode mode) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .put()
                        .uri(uri -> uri.path("cloud-integration").build())
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(mode)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }
}
