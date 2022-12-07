package de.dvdgeisler.iot.dirigera.client.api.http;

import de.dvdgeisler.iot.dirigera.client.api.model.Error;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

public abstract class AbstractClientApi {

    protected final GatewayDiscovery gatewayDiscovery;
    protected final SslContext sslContext;
    protected final HttpClient httpClient;
    protected final WebClient webClient;
    protected final TokenStore tokenStore;

    public AbstractClientApi(final GatewayDiscovery gatewayDiscovery, final String path, final TokenStore tokenStore) throws SSLException {
        this.gatewayDiscovery = gatewayDiscovery;
        this.sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        this.httpClient = HttpClient.create().secure(t -> t.sslContext(this.sslContext));

        this.webClient = gatewayDiscovery.getApiUrl()
                .map(url -> url + path)
                .map(url -> WebClient
                        .builder()
                        .baseUrl(url))
                .map(b -> b.clientConnector(new ReactorClientHttpConnector(this.httpClient)))
                .map(WebClient.Builder::build)
                .block();
        this.tokenStore = tokenStore;
    }

    public AbstractClientApi(final GatewayDiscovery gatewayDiscovery, final TokenStore tokenStore) throws SSLException {
        this(gatewayDiscovery, "", tokenStore);
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
