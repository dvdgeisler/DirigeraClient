package de.dvdgeisler.iot.dirigera.client.api.mdns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class EndpointDiscovery implements ServiceListener {
    private final static Logger log = LoggerFactory.getLogger(EndpointDiscovery.class);

    private String hostname;
    private short port;

    private final JmDNS jmdns;

    public EndpointDiscovery(
            final String hostname,
            final short port,
            final String serviceDomain) throws IOException {
        this.hostname = hostname;
        this.port = port;
        this.jmdns = JmDNS.create(InetAddress.getLocalHost());

        if (this.hostname == null || this.hostname.isBlank() || port == 0) {
            log.info("Auto discover gateway on domain {}", serviceDomain);
            this.jmdns.addServiceListener(serviceDomain, this);
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

    public Mono<InetSocketAddress> getSocketAddress() {
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
                    return new InetSocketAddress(this.hostname, this.port);
                })
                .filter(Objects::nonNull)
                .retryWhen(Retry.fixedDelay(20, Duration.ofSeconds(5)))
                .doOnError(e -> log.error(e.getMessage()));
    }

}
