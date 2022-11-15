package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.RemoteLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class ClientRemoteLinkApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientRemoteLinkApi.class);

    public ClientRemoteLinkApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore) throws SSLException {
        super(String.format("https://%s:%d/v1/remoteLinks/", hostname, port), tokenStore);
    }

    public Mono<Void> updateRemoteLinks(final String remoteLinkId, final RemoteLink remoteLink) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("{remoteLinkId}/targets").build(remoteLinkId))
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(remoteLink)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }
}
