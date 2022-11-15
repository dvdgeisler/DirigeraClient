package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.Home;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.Map;

@Component
public class ClientApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientApi.class);

    public final ClientDeviceApi device;
    public final ClientDeviceSetApi deviceSet;
    public final ClientGatewayApi gateway;
    public final ClientMusicApi music;
    public final ClientOAuthApi oauth;
    public final ClientRemoteLinkApi remoteLink;
    public final ClientRoomApi room;
    public final ClientSceneApi scene;
    public final ClientStepApi step;
    public final ClientUserApi user;

    public ClientApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore,
            final ClientDeviceApi device,
            final ClientDeviceSetApi deviceSet,
            final ClientGatewayApi gateway,
            final ClientMusicApi music,
            final ClientOAuthApi oauth,
            final ClientRemoteLinkApi remoteLink,
            final ClientRoomApi room,
            final ClientSceneApi scene,
            final ClientStepApi step,
            final ClientUserApi user
    ) throws SSLException {
        super(String.format("https://%s:%d/v1/", hostname, port), tokenStore);
        this.device = device;
        this.deviceSet = deviceSet;
        this.gateway = gateway;
        this.music = music;
        this.oauth = oauth;
        this.remoteLink = remoteLink;
        this.room = room;
        this.scene = scene;
        this.step = step;
        this.user = user;
    }

    public Mono<Home> home() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("home").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Home.class);
    }

    public Mono<Map> dump() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("home").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Map.class);
    }

}
