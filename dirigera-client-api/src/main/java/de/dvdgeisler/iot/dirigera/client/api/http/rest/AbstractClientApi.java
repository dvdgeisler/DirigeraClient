package de.dvdgeisler.iot.dirigera.client.api.http.rest;

import de.dvdgeisler.iot.dirigera.client.api.http.HTTPClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.Error;
import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

public abstract class AbstractClientApi extends HTTPClientApi {

    public AbstractClientApi(final RestApiDiscovery discovery, final String path) throws SSLException {
        super(discovery, path, getInsecureHttpClient());
    }

    public AbstractClientApi(final RestApiDiscovery discovery) throws SSLException {
        this(discovery, null);
    }

    public static HttpClient getInsecureHttpClient() throws SSLException {
        final SslContext sslContext;

        sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        return HttpClient.create().secure(t -> t.sslContext(sslContext));
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
