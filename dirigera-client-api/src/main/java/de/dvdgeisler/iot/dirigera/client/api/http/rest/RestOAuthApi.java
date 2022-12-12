package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.auth.Authorize;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.auth.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
public class RestOAuthApi extends AbstractRestApi {
    private final static Logger log = LoggerFactory.getLogger(RestOAuthApi.class);
    private final static MessageDigest DIGEST;
    private final static Base64.Encoder BASE64_ENCODER;
    private final static Base64.Decoder BASE64_DECODER;
    private final static String CODE_ALPHABET;
    private final static int CODE_LENGTH;

    private final static String AUDIENCE;
    private final static String CHALLENGE_METHOD;

    static {
        try {
            DIGEST = MessageDigest.getInstance("sha256");
            BASE64_ENCODER = Base64.getUrlEncoder();
            BASE64_DECODER = Base64.getUrlDecoder();
            CODE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
            CODE_LENGTH = 128;
            AUDIENCE = "homesmart.local";
            CHALLENGE_METHOD = "S256";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final String clientName;
    private final TokenStore tokenStore;

    public RestOAuthApi(
            final RestApiDiscovery discovery,
            @Value("${dirigera.clientname:}") final String clientName,
            final TokenStore tokenStore) throws SSLException {
        super(discovery, "oauth/");

        this.clientName = Optional
                .ofNullable(clientName)
                .filter(Predicate.not(String::isBlank))
                .orElseGet(RestOAuthApi::defaultClientName);
        log.info("Dirigera client name: {}", this.clientName);
        this.tokenStore = tokenStore;
    }

    public Mono<Authorize> authorize(final String challenge) {
        return Mono.just(this.webClient)
                .flatMap(webClient -> webClient
                        .get()
                        .uri(uri -> uri
                                .path("authorize")
                                .queryParam("audience", AUDIENCE)
                                .queryParam("response_type", "code")
                                .queryParam("code_challenge", challenge)
                                .queryParam("code_challenge_method", CHALLENGE_METHOD).build())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Authorize.class));
    }

    public Mono<Void> cancelAuthorize(final Authorize authorize, final String codeVerifier) {
        return Mono.just(this.webClient)
                .flatMap(webClient -> webClient
                        .post()
                        .uri(uri -> uri.path("authorize").build())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters
                                .fromFormData("code", authorize.code.toString())
                                .with("code_verifier", codeVerifier))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Void.class));
    }

    public Mono<Token> tokenExchange(final Authorize authorize, final String codeVerifier) {
        return Mono.just(this.webClient)
                .flatMap(webClient -> webClient
                        .post()
                        .uri(uri -> uri.path("token").build())
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
                        .doOnSuccess(this.tokenStore::setAccessToken));
    }

    /**
     * TODO: No idea what this fun is supposed to do or return
     */
    public Mono<Map> pollAuthStatus(final String transactionId) {
        return Mono.just(this.webClient)
                .flatMap(webClient -> webClient
                        .get()
                        .uri(uri -> uri.path("authorize/{transactionId}").build(transactionId))
                        .headers(this.tokenStore::setBearerAuth)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Map.class));
    }

    public Mono<Token> refreshToken() {
        final String token;

        try {
            token = this.tokenStore.getAccessToken();
        } catch (final IOException e) {
            return Mono.error(e);
        }

        return Mono.just(this.webClient)
                .flatMap(webClient -> webClient
                        .post()
                        .uri(uri -> uri.path("token").build())
                        .headers(this.tokenStore::setBearerAuth)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData("refresh_token", token))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatus::isError, this::onError)
                        .bodyToMono(Token.class)
                        .doOnSuccess(RestOAuthApi.this.tokenStore::setAccessToken));
    }

    public boolean isPaired() {
        return this.tokenStore.hasAccessToken();
    }

    public Mono<Token> pair() {
        final String codeVerifier;
        final String codeChallenge;

        codeVerifier = RestOAuthApi.generateCodeVerifier();
        codeChallenge = RestOAuthApi.calculateCodeChallenge(codeVerifier);

        return this.authorize(codeChallenge)
                .doOnSuccess(authorize -> log.info("Press button on Dirigera Hub to finish pairing"))
                .flatMap(authorize ->
                        this.tokenExchange(authorize, codeVerifier)
                                .retryWhen(Retry.fixedDelay(40, Duration.ofSeconds(3)))
                                .publishOn(Schedulers.boundedElastic())
                                .doOnError(err -> {
                                    log.error(err.getMessage());
                                    this.cancelAuthorize(authorize, codeVerifier).block();
                                })
                )
                .doOnSuccess(token -> log.info("Dirigera Hub successfully paired"));
    }

    public Mono<Token> pairIfRequired() {
        if (this.isPaired()) {
            try {
                return Mono.just(new Token(this.tokenStore.getAccessToken()));
            } catch (IOException e) {
                return Mono.error(e);
            }
        }
        log.info("No access token found. Pairing required.");
        return this.pair();
    }

    private static String defaultClientName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        return UUID.randomUUID().toString();
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
}
