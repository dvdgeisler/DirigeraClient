package de.dvdgeisler.iot.dirigera.client.api.mdns;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import javax.net.ssl.SSLException;
import java.io.IOException;

@Component
public class RestApiDiscovery extends HTTPEndpointDiscovery {
    private final static Logger log = LoggerFactory.getLogger(RestApiDiscovery.class);
    private final static String SERVICE_DOMAIN = "_ihsp._tcp.local.";

    public RestApiDiscovery(@Value("${dirigera.rest.hostname:}") final String hostname,
                            @Value("${dirigera.rest.port:0}") final short port) throws IOException {
        super(hostname, port, SERVICE_DOMAIN);
    }

    public Mono<String> getApiUrl() {
        return super.getSocketAddress().map(isa -> String.format("https://%s:%d/v1/", isa.getHostString(), isa.getPort()));
    }

    private void secure(final SslProvider.SslContextSpec ssl) {
        try {
            ssl.sslContext(SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build());
        } catch (SSLException e) {
            log.error(e.getMessage());
        }
    }

    private HttpClient secure(final HttpClient httpClient) {
        return httpClient.secure(this::secure);
    }

    @Override
    public Mono<HttpClient> getHttpClient() {

        return super.getHttpClient().map(this::secure);
    }
}
