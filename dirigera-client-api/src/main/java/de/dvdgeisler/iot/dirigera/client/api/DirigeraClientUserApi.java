package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.model.user.User;
import de.dvdgeisler.iot.dirigera.client.api.model.user.UserName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.List;

@Component
public class DirigeraClientUserApi extends AbstractApi {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientUserApi.class);

    public DirigeraClientUserApi(
            @Value("${dirigera.hostname}") final String hostname,
            @Value("${dirigera.port:8443}") final short port,
            final TokenStore tokenStore) throws SSLException {
        super(String.format("https://%s:%d/v1/users/", hostname, port), tokenStore);
    }


    public Mono<Void> deleteUser(final String id) {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("{id}").build(id))
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<List<User>> users() {
        return this.webClient
                .get()
                .uri(UriBuilder::build)
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    public Mono<User> getUser() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("me").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(User.class);
    }


    public Mono<Void> setUserName(final UserName name) {
        return this.webClient
                .patch()
                .uri(uri -> uri.path("me").build())
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

}
