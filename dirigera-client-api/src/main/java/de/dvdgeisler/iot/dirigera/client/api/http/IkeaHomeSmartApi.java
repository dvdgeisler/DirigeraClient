package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.Endpoints;
import de.dvdgeisler.iot.dirigera.client.api.model.integrations.Integrations;
import de.dvdgeisler.iot.dirigera.client.api.model.integrations.alexa.AlexaAuthentication;
import de.dvdgeisler.iot.dirigera.client.api.model.integrations.google.GoogleAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

@Component
public class IkeaHomeSmartApi extends AbstractClientApi {

    public IkeaHomeSmartApi(
            @Value("${ikea.url:https://system.v2.config.homesmart.ikea.com/}") final String ikeaApiUrl, 
            final TokenStore tokenStore) throws SSLException {
        super(ikeaApiUrl, tokenStore);
    }

    public Mono<Void> disableAlexa() {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("v1/integrations/alexa").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> disableGoogle() {
        return this.webClient
                .delete()
                .uri(uri -> uri.path("v1/integrations/google").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<Void> enableAlexa(final AlexaAuthentication authentication) {
        return this.webClient
                .post()
                .uri(uri -> uri.path("v1/integrations/alexa").build())
                .headers(this.tokenStore::setBearerAuth)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authentication)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Void.class);
    }

    public Mono<GoogleAuthentication> enableGoogle() {
        return this.webClient
                .post()
                .uri(uri -> uri.path("v1/integrations/google").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(GoogleAuthentication.class);
    }

    public Mono<Integrations> integrations() {
        return this.webClient
                .get()
                .uri(uri -> uri.path("v1/integrations").build())
                .headers(this.tokenStore::setBearerAuth)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Integrations.class);
    }

    public Mono<Endpoints> endpoints(final String region) {
        return this.webClient
                .get()
                .uri(uri -> uri.path("app").queryParam("region", region).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, this::onError)
                .bodyToMono(Endpoints.class);
    }
}
