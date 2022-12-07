package de.dvdgeisler.iot.dirigera.client.api.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GatewayDiscovery implements ServiceListener {
    private final static Logger log = LoggerFactory.getLogger(GatewayDiscovery.class);

    private String hostname;
    private short port;

    private final JmDNS jmdns;

    public GatewayDiscovery(@Value("${dirigera.hostname:}") final String hostname,
                            @Value("${dirigera.port:0}") final short port) throws IOException {
        this.hostname = hostname;
        this.port = port;
        this.jmdns = JmDNS.create(InetAddress.getLocalHost());

        if (this.hostname == null || this.hostname.isBlank() || port == 0) {
            log.info("Auto discover gateway");
            this.jmdns.addServiceListener("_ihsp._tcp.local.", this);
        }

    }

    @Override
    public void serviceAdded(final ServiceEvent serviceEvent) {
        final ServiceInfo info;
        info = serviceEvent.getInfo();

        log.info("Gateway discovered: name={}, server={}, application={}, protocol={}, domain={}",
                info.getName(),
                info.getServer(),
                info.getApplication(),
                info.getProtocol(),
                info.getDomain());

        this.hostname = info.getName();
    }

    @Override
    public void serviceRemoved(final ServiceEvent serviceEvent) {

    }

    @Override
    public void serviceResolved(final ServiceEvent serviceEvent) {
        final ServiceInfo info;
        info = serviceEvent.getInfo();

        log.info("Gateway rest API discovered: name={}, port={}, ip={}, server={}, application={}, protocol={}, domain={}",
                info.getName(),
                info.getPort(),
                Arrays.stream(info.getInetAddresses())
                        .map(InetAddress::getHostAddress)
                        .collect(Collectors.joining(",")),
                info.getServer(),
                info.getApplication(),
                info.getProtocol(),
                info.getDomain());

        this.hostname = info.getName();
        this.port = (short) info.getPort();
    }

    public Mono<String> getApiUrl() {
        return Mono.fromSupplier(() -> {
                    if (this.hostname == null || this.hostname.isBlank())
                        throw new RuntimeException("" +
                                "Hostname not discovered. " +
                                "You may use the properties " +
                                "\"dirigera.hostname\" and \"dirigera.port\" " +
                                "to specify the gateway endpoint.");
                    if (this.port <= 0)
                        throw new RuntimeException("" +
                                "Port not discovered. " +
                                "You may use the properties " +
                                "\"dirigera.hostname\" and \"dirigera.port\" " +
                                "to specify the gateway endpoint.");
                    return String.format("https://%s:%d/v1/", this.hostname, this.port);
                })
                .filter(Objects::nonNull)
                .retryWhen(Retry.fixedDelay(20, Duration.ofSeconds(5)))
                .doOnError(e -> log.error(e.getMessage()));
    }
}
