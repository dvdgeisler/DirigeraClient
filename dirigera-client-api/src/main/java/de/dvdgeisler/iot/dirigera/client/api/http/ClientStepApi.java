package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.StepAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class ClientStepApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientStepApi.class);

    public ClientStepApi(
            final GatewayDiscovery gatewayDiscovery,
            final TokenStore tokenStore) throws SSLException {
        super(gatewayDiscovery, "step/", tokenStore);
    }

    public Mono<Void> stepDeviceGroup(final String targetId, final DeviceType deviceType, final StepAttributes attributes) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("{targetId}").queryParam("deviceType", deviceType).build(targetId))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }
}
