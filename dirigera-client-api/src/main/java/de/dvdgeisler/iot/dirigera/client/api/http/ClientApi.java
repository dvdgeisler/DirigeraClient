package de.dvdgeisler.iot.dirigera.client.api.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.model.Home;
import de.dvdgeisler.iot.dirigera.client.api.model.events.Event;
import de.dvdgeisler.iot.dirigera.client.api.model.events.PingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Component
public class ClientApi extends AbstractClientApi {
    private final static Logger log = LoggerFactory.getLogger(ClientApi.class);
    private final static String WEBSOCKET_SPECVERSION = "1.1.0";
    private final static String WEBSOCKET_SOURCE_URN = String.format("urn:%s:%s", ClientApi.class.getPackageName(), ClientApi.class.getClass().getSimpleName());
    private final static Duration WEBSOCKET_PING_DELAY = Duration.ofSeconds(10);
    private final static Duration WEBSOCKET_PING_TIMEOUT = WEBSOCKET_PING_DELAY.plusSeconds(1);
    private final String hostname;
    private final short port;
    private final ObjectMapper objectMapper;

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
            final ObjectMapper objectMapper,
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
        this.hostname = hostname;
        this.port = port;
        this.objectMapper = objectMapper;
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

    public Mono<Void> websocket(final WebSocketHandler consumer) {
        final URI uri;

        uri = URI.create(String.format("https://%s:%d/v1/", this.hostname, this.port));
        return this.oauth.pairIfRequired()
                .map(token -> String.format("Bearer %s", token.access_token))
                .map(bearer -> this.httpClient
                        .headers(headers -> headers.add(HttpHeaders.AUTHORIZATION, bearer))
                        .keepAlive(true))
                .map(ReactorNettyWebSocketClient::new)
                .flatMap(client -> client.execute(uri, consumer));
    }

    public Mono<Void> websocket(final Consumer<Event> consumer, final BooleanSupplier run) {
        return this.websocket(session -> {

            Schedulers.boundedElastic().schedule(
                    () -> {
                        final Thread thread;

                        thread = Thread.currentThread();
                        log.info("Start ping thread: id={}, name={}", thread.getId(), thread.getName());
                        session.send(this.buildPingMessage(session).delayElement(WEBSOCKET_PING_DELAY))
                                .repeat(run)
                                .blockLast();
                        log.info("Finished ping thread: id={}, name={}", thread.getId(), thread.getName());
                    });
            return Mono.just(session)
                    .flatMapMany(WebSocketSession::receive)
                    .timeout(WEBSOCKET_PING_TIMEOUT)
                    .map(WebSocketMessage::getPayload)
                    .map(DataBuffer::asInputStream)
                    .flatMap(i -> {
                        try {
                            return Flux.just(this.objectMapper.readValue(i, Event.class));
                        } catch (IOException e) {
                            return Flux.error(e);
                        }
                    })
                    .doOnNext(consumer)
                    .repeat(run)
                    .then();
        });
    }

    private Mono<WebSocketMessage> buildPingMessage(final WebSocketSession session) {
        return Mono.just(new PingEvent(
                        UUID.randomUUID().toString(),
                        LocalDateTime.now(),
                        WEBSOCKET_SPECVERSION,
                        WEBSOCKET_SOURCE_URN))
                .flatMap(pingEvent -> {
                    try {
                        return Mono.just(this.objectMapper.writeValueAsString(pingEvent));
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                })
                .map(session::textMessage);
    }
}


