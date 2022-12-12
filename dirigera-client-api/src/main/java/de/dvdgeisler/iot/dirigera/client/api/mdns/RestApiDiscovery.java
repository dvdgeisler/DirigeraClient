package de.dvdgeisler.iot.dirigera.client.api.mdns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestApiDiscovery extends EndpointDiscovery {
    private final static String SERVICE_DOMAIN = "_ihsp._tcp.local.";
    public RestApiDiscovery(@Value("${dirigera.hostname:}") final String hostname,
                            @Value("${dirigera.port:0}") final short port) throws IOException {
        super(hostname, port, SERVICE_DOMAIN);
    }
}
