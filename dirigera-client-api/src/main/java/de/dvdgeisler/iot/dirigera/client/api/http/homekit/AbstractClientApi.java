package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import de.dvdgeisler.iot.dirigera.client.api.http.HTTPClientApi;
import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;

import javax.net.ssl.SSLException;

public abstract class AbstractClientApi extends HTTPClientApi {

    public AbstractClientApi(final RestApiDiscovery discovery, final String path) throws SSLException {
        super(discovery, path, null);
    }
}
