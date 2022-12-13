package de.dvdgeisler.iot.dirigera.client.api.http.homekit;

import de.dvdgeisler.iot.dirigera.client.api.http.HTTPClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.homekit.tlv.DirigeraTLV;
import de.dvdgeisler.iot.dirigera.client.api.mdns.HomekitDiscovery;
import de.dvdgeisler.iot.dirigera.client.api.mdns.RestApiDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@ComponentScan(basePackageClasses = {RestApiDiscovery.class})
public abstract class AbstractHomekitApi extends HTTPClientApi {
    private final static Logger log = LoggerFactory.getLogger(AbstractHomekitApi.class);

    public AbstractHomekitApi(final HomekitDiscovery discovery, final String path) {
        super(discovery, path, null);
    }

    protected Mono<DirigeraTLV> send(final DirigeraTLV message) {
        return Mono.fromSupplier(() -> {
                    try {
                        final byte[] payload;
                        log.debug("Send TLV message:\n{}", message);
                        payload = message.encode();
                        return payload;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(payload -> this.webClient.post()
                        .contentLength(payload.length) // crucial
                        .body((request, context) -> request.writeWith((s) ->
                                Flux.just(payload)
                                        .map(p -> request.bufferFactory().wrap(p))
                                        .subscribe(s)))
                        .retrieve()
                        .bodyToMono(byte[].class))
                .flatMap(bytes -> {
                    try {
                        return Mono.just(new DirigeraTLV(bytes));
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                })
                .flatMap(tlv -> {
                    try {
                        tlv.checkForError();
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                    return Mono.just(tlv);
                })
                .doOnSuccess(tlv -> log.debug("Received TLV message:\n{}", tlv));
    }

}
