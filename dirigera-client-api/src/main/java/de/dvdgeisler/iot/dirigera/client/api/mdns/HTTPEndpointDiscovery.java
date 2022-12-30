package de.dvdgeisler.iot.dirigera.client.api.mdns;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.io.IOException;

public abstract class HTTPEndpointDiscovery extends EndpointDiscovery {

    private final ConnectionProvider connectionProvider;

    public HTTPEndpointDiscovery(
            final String hostname,
            final short port,
            final String serviceDomain) throws IOException {
        super(hostname, port, serviceDomain);

        this.connectionProvider = ConnectionProvider.create(serviceDomain, 1, false);
    }

    public abstract Mono<String> getApiUrl();

    public Mono<HttpClient> getHttpClient() {
        return this.getApiUrl().map(url->HttpClient.create(this.connectionProvider));
    }
}
