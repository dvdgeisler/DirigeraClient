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

    private final ClientOAuthApi oauth;

    public ClientStepApi(
            final GatewayDiscovery gatewayDiscovery,
            final ClientOAuthApi oauth) throws SSLException {
        super(gatewayDiscovery, "step/");
        this.oauth = oauth;
    }

    public Mono<Void> stepDeviceGroup(final String targetId, final DeviceType deviceType, final StepAttributes attributes) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .post()
                        .uri(uri -> uri.path("{targetId}").queryParam("deviceType", deviceType).build(targetId))
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(attributes)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }
}
