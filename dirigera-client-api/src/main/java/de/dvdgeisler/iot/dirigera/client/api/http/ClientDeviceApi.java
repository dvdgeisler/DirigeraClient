package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.device.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.List;

@Component
public class ClientDeviceApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientDeviceApi.class);

    public ClientDeviceApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore) throws SSLException {
        super(String.format("https://%s:%d/v1/devices/", hostname, port), tokenStore);
    }

    public Mono<Void> deleteDevice(final String id) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<List<Device<?,?>>> devices() {
        return this.webClient
                .get()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<Void> editDeviceState(final String id, final List<? extends DeviceStateCommand<?>> state) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(state)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> editDeviceConfiguration(final String id, final List<? extends DeviceConfigurationCommand<?>> attributes) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> editDeviceGroup(final String id, final DeviceGroupType groupType, final DeviceType deviceType, final List<DeviceStateCommand> attributes) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("{groupType}/{id}").queryParam("deviceType", deviceType).build(groupType, id))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> editDeviceType(final DeviceType deviceType, final List<DeviceStateCommand> attributes) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("all").queryParam("deviceType", deviceType).build())
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> identifyDevice(final String id, final DeviceIdentifyPeriod period) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("{id}/identify").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(period)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Device> device(final String id) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Device.class);
    }

    public Mono<Void> stopIdentifyDevice(final String id, final DeviceIdentifyPeriod period) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("{id}/identify").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(period)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }
}
