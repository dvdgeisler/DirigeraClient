package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.DirigeraRequestException;
import de.dvdgeisler.iot.dirigera.client.api.http.json.*;
import de.dvdgeisler.iot.dirigera.client.api.http.json.Error;
import de.dvdgeisler.iot.dirigera.client.api.http.json.auth.Authorize;
import de.dvdgeisler.iot.dirigera.client.api.http.json.auth.Token;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.*;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.gateway.GatewayEnvironment;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.gateway.GatewayPersistentMode;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.gateway.GatewayStatus;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.DeviceSetAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.RoomAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.json.music.Music;
import de.dvdgeisler.iot.dirigera.client.api.http.json.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.http.json.scene.SceneAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.json.user.User;
import de.dvdgeisler.iot.dirigera.client.api.http.json.user.UserName;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
public class DirigeraClientApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientApi.class);
    private final static MessageDigest DIGEST;
    private final static Base64.Encoder BASE64_ENCODER;
    private final static Base64.Decoder BASE64_DECODER;
    private final static String CODE_ALPHABET;
    private final static int CODE_LENGTH;
    private static final String ACCESS_TOKEN_FILE_NAME;

    private final static String AUDIENCE;
    private final static String CHALLENGE_METHOD;

    static {
        try {
            DIGEST = MessageDigest.getInstance("sha256");
            BASE64_ENCODER = Base64.getUrlEncoder();
            BASE64_DECODER = Base64.getUrlDecoder();
            CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
            CODE_LENGTH = 128;
            ACCESS_TOKEN_FILE_NAME = "dirigera_access_token";
            AUDIENCE = "homesmart.local";
            CHALLENGE_METHOD = "S256";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private String accessToken;
    private final WebClient webClient;
    private final String clientName;

    public DirigeraClientApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            @Value("${dirigera.clientname:}") final String clientName) throws SSLException {
        final SslContext sslContext;
        final HttpClient httpClient;
        sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        this.accessToken = null;
        this.webClient = WebClient
                .builder()
                .baseUrl(String.format("https://%s:%d/v1/", hostname, port))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        log.info("Initialized web-client: host={}, port={}", hostname, port);

        this.clientName = Optional
                .ofNullable(clientName)
                .filter(Predicate.not(String::isBlank))
                .orElseGet(DirigeraClientApi::defaultClientName);
        log.info("Dirigera client name: {}", this.clientName);

        try {
            log.info("Load access token");
            this.loadAccessToken();
        } catch (IOException e) {
            log.error("Cannot read access token: {}", e.getMessage());
        }
    }

    private static String defaultClientName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        return UUID.randomUUID().toString();
    }

    public Mono<Authorize> authorize(final String challenge) {
        return this.webClient
                .get()
                .uri(uri -> uri
                        .path("oauth/authorize")
                        .queryParam("audience", AUDIENCE)
                        .queryParam("response_type", "code")
                        .queryParam("code_challenge", challenge)
                        .queryParam("code_challenge_method", CHALLENGE_METHOD).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Authorize.class);
    }

    public Mono<Void> cancelAuthorize(final Authorize authorize, final String codeVerifier) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("oauth/authorize").build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("code", authorize.code.toString())
                        .with("code_verifier", codeVerifier))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Token> tokenExchange(final Authorize authorize, final String codeVerifier) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("oauth/token").build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("code", authorize.code.toString())
                        .with("code_verifier", codeVerifier)
                        .with("name", this.clientName)
                        .with("grant_type", "authorization_code"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Token.class)
                .doOnSuccess(this::setAccessToken);
    }

    public Mono<Token> pair() {
        final String codeVerifier;
        final String codeChallenge;

        codeVerifier = DirigeraClientApi.generateCodeVerifier();
        codeChallenge = DirigeraClientApi.calculateCodeChallenge(codeVerifier);

        return this.authorize(codeChallenge)
                .doOnSuccess(authorize -> log.info("Press button on Dirigera Hub to finish pairing"))
                .flatMap(authorize ->
                        this.tokenExchange(authorize, codeVerifier)
                                .retryWhen(Retry.fixedDelay(10, Duration.ofSeconds(5)))
                                .publishOn(Schedulers.boundedElastic())
                                .doOnError(err->{
                                    log.error(err.getMessage());
                                    this.cancelAuthorize(authorize, codeVerifier).block();
                                })
                )
                .doOnSuccess(token -> log.info("Dirigera Hub successfully paired"))
                .doOnSuccess(this::setAccessToken);
    }

    public boolean hasAccessToken() {
        return this.accessToken != null && !this.accessToken.isBlank();
    }

    public Mono<GatewayStatus> status() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("hub/status").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(GatewayStatus.class);
    }

    public Mono<Home> home() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("home").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Home.class);
    }

    public Mono<Void> checkFirmwareUpdate() {
        return this.webClient
                .put()
                .uri(uri -> uri.path("hub/ota/check").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Identifier> createDeviceSet(final String name, final String icon) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("device-set").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("icon", icon))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Identifier.class);
    }

    public Mono<Identifier> createRoom(final String name, final String icon, final String color) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("rooms").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("name", name)
                        .with("icon", icon)
                        .with("color", color))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Identifier.class);
    }

    /**
     * TODO: fails if any trigger or action is set: "triggers[0]" does not match any of the allowed types, "actions[0]" does not match any of the allowed types
     */
    public Mono<Identifier> createScene(final Scene scene) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("scenes").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(scene)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Identifier.class);
    }

    public Mono<Void> deleteDevice(final String id) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("devices/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteDeviceSet(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("device-set/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteRoom(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("rooms/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteScene(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("scenes/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteUser(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("users/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<List<Device<?>>> devices() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("devices").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<List<Scene>> scenes() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("scenes").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<List<Scene>>() {
                });
    }

    public Mono<List<Room>> rooms() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("rooms").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<List<Room>>() {
                });
    }

    public Mono<List<User>> users() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("users").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<Void> editDevice(final String id, final List<? extends DeviceState> attributes) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("devices/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> editDeviceGroup(final String id, final DeviceGroupType groupType, final DeviceType deviceType, final List<DeviceState> attributes) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("devices/{groupType}/{id}").queryParam("deviceType", deviceType).build(groupType, id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> editDeviceType(final DeviceType deviceType, final List<DeviceState> attributes) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("devices/all").queryParam("deviceType", deviceType).build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Scene> getScene(final String id) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("scenes/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Scene.class);
    }

    public Mono<User> getUser() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("users/me").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(User.class);
    }

    public Mono<Void> identifyDevice(final String id, final DeviceIdentifyPeriod period) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("devices/{id}/identify").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
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
                .uri(uri -> uri.path("devices/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Device.class);
    }

    public Mono<Void> installFirmwareUpdate(final String id) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("hub/ota/update").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Music> music() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("music").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Music.class);
    }

    /**
     * TODO: No idea what this fun is supposed to do or return
     */
    public Mono<Map> pollAuthStatus(final String transactionId) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("oauth/authorize/{transactionId}").build(transactionId))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Map.class);
    }

    public Mono<Token> refreshToken() {
        return this.webClient
                .post()
                .uri(uri -> uri.path("oauth/token").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("refresh_token", this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Token.class)
                .doOnSuccess(DirigeraClientApi.this::setAccessToken);
    }

    public void setAccessToken(final Token token) {
        this.accessToken = token.access_token;
        try {
            this.saveAccessToken();
        } catch (IOException e) {
            log.error("Error while saving new access token {}: {}", token.access_token, e.getMessage());
        }
    }

    /**
     * TODO: results in 404 error, might not supported in release mode
     */
    public Mono<Void> setFirmwareEnvironment(final GatewayEnvironment environment) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("hub/ota/environment").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(environment)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> setPersistentMode(final GatewayPersistentMode mode) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("hub/cloud-integration").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mode)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> setUserName(final UserName name) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("users/me").build())
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> stepDeviceGroup(final String targetId, final DeviceType deviceType, final StepAttributes attributes) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("step/{targetId}").queryParam("deviceType", deviceType).build(targetId))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> stopIdentifyDevice(final String id, final DeviceIdentifyPeriod period) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("devices/{id}/identify").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(period)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> triggerScene(final String id) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("scenes/{id}/trigger").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> undoScene(final String id) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("scenes/{id}/trigger").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateDeviceSet(final String id, final DeviceSetAttributes attributes) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("device-set/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateRoom(final String id, final RoomAttributes attributes) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("rooms/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateScene(final String id, final SceneAttributes attributes) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("scenes/{id}").build(id))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> updateRemoteLinks(final String remoteLinkId, final Map<String, List<String>> attributes) {
        return this.webClient
                .put()
                .uri(uri -> uri.path("remoteLinks/{remoteLinkId}/targets").build(remoteLinkId))
                .headers(h -> h.setBearerAuth(this.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(attributes)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    private Mono<? extends Throwable> onError(final ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(Error.class)
                .map(DirigeraRequestException::new);
    }


    private static String generateCodeVerifier() {
        final StringBuilder code;
        final ThreadLocalRandom rnd;

        rnd = ThreadLocalRandom.current();
        code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++)
            code.append(CODE_ALPHABET.charAt(rnd.nextInt(0, CODE_ALPHABET.length())));

        return code.toString();
    }

    private static String calculateCodeChallenge(final String codeVerifier) {
        final String base64;
        final byte[] hash;

        hash = DIGEST.digest(codeVerifier.getBytes(US_ASCII));
        base64 = BASE64_ENCODER.encodeToString(hash);
        return base64.substring(0, base64.length() - 1); //remove trailing '='
    }

    private void saveAccessToken() throws IOException {
        final BufferedWriter writer;

        writer = new BufferedWriter(new FileWriter(ACCESS_TOKEN_FILE_NAME));
        writer.write(this.accessToken);
        writer.close();
    }

    private void loadAccessToken() throws IOException {
        final BufferedReader reader;

        this.accessToken = null;
        reader = new BufferedReader(new FileReader(ACCESS_TOKEN_FILE_NAME));
        this.accessToken = reader.readLine();
        reader.close();

        if (this.accessToken != null && this.accessToken.isBlank())
            this.accessToken = null;
    }
}
