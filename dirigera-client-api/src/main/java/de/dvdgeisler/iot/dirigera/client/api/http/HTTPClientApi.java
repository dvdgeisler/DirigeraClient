package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.mdns.EndpointDiscovery;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.Optional;
import java.util.function.Predicate;

public class HTTPClientApi {
    protected final EndpointDiscovery discovery;
    protected final HttpClient httpClient;
    protected final WebClient webClient;

    public HTTPClientApi(final EndpointDiscovery discovery, final String path, final HttpClient httpClient) {
        this.discovery = discovery;
        this.httpClient = httpClient;
        this.webClient = this.discovery.getApiUrl()
                .map(url -> Optional
                        .ofNullable(path)
                        .filter(Predicate.not(String::isBlank))
                        .map(p -> url + path)
                        .orElse(url))
                .map(url -> WebClient
                        .builder()
                        .baseUrl(url))
                .map(b -> Optional
                        .ofNullable(httpClient)
                        .map(ReactorClientHttpConnector::new)
                        .map(b::clientConnector)
                        .orElse(b))
                .map(WebClient.Builder::build)
                .block();
    }
}
