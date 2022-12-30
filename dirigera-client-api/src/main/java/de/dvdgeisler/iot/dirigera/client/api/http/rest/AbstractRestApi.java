package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.http.HTTPClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.Error;
import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;

public abstract class AbstractRestApi extends HTTPClientApi {

    public AbstractRestApi(final RestApiDiscovery discovery, final String path) throws SSLException {
        super(discovery, path);
    }

    public AbstractRestApi(final RestApiDiscovery discovery) throws SSLException {
        this(discovery, null);
    }


    protected Mono<? extends Throwable> onError(final ClientResponse clientResponse) {
        if (clientResponse.rawStatusCode() == 404)
            return clientResponse
                    .bodyToMono(String.class)
                    .map(Jsoup::parse)
                    .map(Document::body)
                    .map(Element::text)
                    .map(RequestException::new);
        return clientResponse
                .bodyToMono(Error.class)
                .map(RequestException::new);
    }
}
