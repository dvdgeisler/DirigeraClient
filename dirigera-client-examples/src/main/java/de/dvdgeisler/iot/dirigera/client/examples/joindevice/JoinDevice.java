package de.dvdgeisler.iot.dirigera.client.examples.joindevice;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway.GatewayDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * Join a device to dirigera hub
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class JoinDevice {
    private final static Logger log = LoggerFactory.getLogger(JoinDevice.class);


    @Bean
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
            final List<Device> devices;
            final List<GatewayDevice> gateways;
            final Device device;

            // fetch all known devices
            devices = api.device.all()
                    .flatMapMany(Flux::fromIterable)
                    .cast(Device.class)
                    .collectList()
                    .block();

            // enable joining
            gateways = api.device.gateway.all()
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(api.device.gateway::permittingJoin)
                    .collectList()
                    .block();

            try {
                // check for new devices
                device = api.device.all()
                        .flatMapMany(Flux::fromIterable)
                        .filter(d -> devices.stream().noneMatch(kd -> Objects.equals(kd.id, d.id)))
                        .single()
                        .retryWhen(Retry.fixedDelay(120, Duration.ofSeconds(1)))
                        .block();
                log.info("New device {}: model={}, manufacturer={}, type={}, category={}",
                        device.id,
                        device.attributes.model,
                        device.attributes.manufacturer,
                        device.deviceType,
                        device.type);
            } catch (final Throwable ex) {
                log.error(ex.getMessage());
            } finally {
                // disable joining
                Mono.just(gateways)
                        .flatMapMany(Flux::fromIterable)
                        .flatMap(api.device.gateway::forbidJoin)
                        .collectList()
                        .block();
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(JoinDevice.class, args).close();
    }


}
