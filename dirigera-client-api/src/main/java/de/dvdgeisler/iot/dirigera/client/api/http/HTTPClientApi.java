package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.mdns.EndpointDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.mdns.HTTPEndpointDiscovery;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.Optional;
import java.util.function.Predicate;

public class HTTPClientApi {
    protected final HTTPEndpointDiscovery discovery;
    protected final HttpClient httpClient;
    protected final WebClient webClient;

    public HTTPClientApi(final HTTPEndpointDiscovery discovery, final String path) {
        this.discovery = discovery;
        this.httpClient = discovery.getHttpClient().block();
        this.webClient = discovery.getApiUrl()
                .map(url -> Optional
                        .ofNullable(path)
                        .filter(Predicate.not(String::isBlank))
                        .map(p -> url + path)
                        .orElse(url))
                .map(url -> WebClient
                        .builder()
                        .baseUrl(url))
                .map(b -> b.clientConnector(new ReactorClientHttpConnector(this.httpClient)))
                .map(WebClient.Builder::build)
                .block();
    }

    public EndpointDiscovery getDiscovery() {
        return this.discovery;
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public WebClient getWebClient() {
        return this.webClient;
    }
}
