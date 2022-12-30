package de.dvdgeisler.iot.dirigera.client.api.mdns;

import de.dvdgeisler.iot.dirigera.client.api.http.homekit.crypto.Cryptographer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

@Component
public class HomekitDiscovery extends HTTPEndpointDiscovery {
    private final static Logger log = LoggerFactory.getLogger(HomekitDiscovery.class);
    private final static String SERVICE_DOMAIN = "_hap._tcp.local.";

    private final Cryptographer cryptographer;

    public HomekitDiscovery(@Value("${dirigera.homekit.hostname:}") final String hostname,
                            @Value("${dirigera.homekit.port:0}") final short port,
                            final Cryptographer cryptographer) throws IOException {
        super(hostname, port, SERVICE_DOMAIN);
        this.cryptographer = cryptographer;
    }

    public Mono<String> getApiUrl() {
        return super.getSocketAddress().map(isa -> String.format("http://%s:%d/", isa.getHostString(), isa.getPort()));
    }

    @Override
    public Mono<HttpClient> getHttpClient() {
        return super.getHttpClient().map(httpClient -> httpClient.tcpConfiguration(this.cryptographer::tcpSetup));
    }

}
