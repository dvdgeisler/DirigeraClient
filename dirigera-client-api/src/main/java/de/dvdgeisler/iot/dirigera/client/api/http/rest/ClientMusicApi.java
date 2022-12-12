package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class ClientMusicApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientMusicApi.class);

    private final ClientOAuthApi oauth;

    public ClientMusicApi(
            final RestApiDiscovery discovery,
            final ClientOAuthApi oauth) throws SSLException {
        super(discovery, "music/");
        this.oauth = oauth;
    }

    public Mono<Music> music() {
        return this.oauth.pairIfRequired()
                .map(token -> token.access_token)
                .flatMap(token -> this.webClient
                        .get()
                        .uri(UriBuilder::build)
                        .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Music.class));
    }
}
