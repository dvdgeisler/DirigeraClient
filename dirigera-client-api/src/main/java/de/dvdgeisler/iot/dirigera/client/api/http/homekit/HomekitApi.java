package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import de.dvdgeisler.iot.dirigera.client.api.http.HTTPClientApi;
import de.dvdgeisler.iot.dirigera.client.api.mdns.HomekitDiscovery;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HomekitApi extends HTTPClientApi {

    public HomekitApi(final HomekitDiscovery discovery) {
        super(discovery, "/accessories");
    }

    public Map test() {
        return this.webClient
                .get()
                .headers(httpHeaders -> httpHeaders.set("Authorization", "911-18-871"))
                .retrieve()
                .bodyToMono(Map.class).block();
    }
}
