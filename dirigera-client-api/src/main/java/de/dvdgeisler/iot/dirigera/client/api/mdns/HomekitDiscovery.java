package de.dvdgeisler.iot.dirigera.client.api.mdns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class HomekitDiscovery extends EndpointDiscovery {
    private final static String SERVICE_DOMAIN = "_hap._tcp.local.";
    public HomekitDiscovery(@Value("${dirigera.homekit.hostname:}") final String hostname,
                            @Value("${dirigera.homekit.port:0}") final short port) throws IOException {
        super(hostname, port, SERVICE_DOMAIN);
    }

    public Mono<String> getApiUrl() {
        return super.getEndpoint().map(url -> String.format("http://%s/", url));
    }
}
