package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.RemoteLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class ClientRemoteLinkApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientRemoteLinkApi.class);

    private final ClientOAuthApi oauth;

    public ClientRemoteLinkApi(
            final GatewayDiscovery gatewayDiscovery,
            final ClientOAuthApi oauth) throws SSLException {
        super(gatewayDiscovery, "remoteLinks/");
        this.oauth = oauth;
    }

    public Mono<Void> updateRemoteLinks(final String remoteLinkId, final RemoteLink remoteLink) {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .put()
                        .uri(uri -> uri.path("{remoteLinkId}/targets").build(remoteLinkId))
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(remoteLink)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }
}
