package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import de.dvdgeisler.iot.dirigera.client.api.http.HTTPClientApi;
import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;

public abstract class AbstractHomekitApi extends HTTPClientApi {

    public AbstractHomekitApi(final RestApiDiscovery discovery, final String path) {
        super(discovery, path, null);
    }
}
