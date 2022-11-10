package de.dvdgeisler.iot.dirigera.client.api;

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
public class DirigeraClientApi extends AbstractApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientApi.class);

    public final DirigeraClientDeviceApi device;
    public final DirigeraClientDeviceSetApi deviceSet;
    public final DirigeraClientGatewayApi gateway;
    public final DirigeraClientMusicApi music;
    public final DirigeraClientOAuthApi oauth;
    public final DirigeraClientRemoteLinkApi remoteLink;
    public final DirigeraClientRoomApi room;
    public final DirigeraClientSceneApi scene;
    public final DirigeraClientStepApi step;
    public final DirigeraClientUserApi user;

    public DirigeraClientApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore,
            final DirigeraClientDeviceApi device,
            final DirigeraClientDeviceSetApi deviceSet,
            final DirigeraClientGatewayApi gateway,
            final DirigeraClientMusicApi music,
            final DirigeraClientOAuthApi oauth,
            final DirigeraClientRemoteLinkApi remoteLink,
            final DirigeraClientRoomApi room,
            final DirigeraClientSceneApi scene,
            final DirigeraClientStepApi step,
            final DirigeraClientUserApi user
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
