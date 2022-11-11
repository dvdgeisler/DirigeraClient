package de.dvdgeisler.iot.dirigera.client.examples.devicesets;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

/**
 * Create, edit, and delete a device-set
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class DeviceSets {
    private final static Logger log = LoggerFactory.getLogger(DeviceSets.class);
    private final DirigeraClientApi api;

    public DeviceSets(final DirigeraClientApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            DeviceSet deviceSet;
            this.api.oauth.pairIfRequired().block();

            deviceSet = this.api.deviceSet.createDeviceSet("My-First-Device-Set", "Icon")
                    .delayElement(Duration.ofSeconds(1))
                    .map(i -> i.id)
                    .flatMap(this::getDeviceSet)
                    .switchIfEmpty(Mono.error(new RuntimeException("Created Device-Set not found")))
                    .doOnSuccess(ds -> log.info("Created Device-Set {}: name={}, icon={}", ds.id, ds.attributes.name, ds.attributes.icon))
                    .block();

            deviceSet.attributes.name = "Updated Device-Set name";
            this.api.deviceSet.updateDeviceSet(deviceSet.id, deviceSet.attributes)
                    .block();
            Thread.sleep(1000); // wait 1s to take effect
            deviceSet = this.getDeviceSet(deviceSet.id)
                    .switchIfEmpty(Mono.error(new RuntimeException("Created Device-Set not found")))
                    .doOnSuccess(ds -> log.info("Updated Device-Set {}: name={}, icon={}", ds.id, ds.attributes.name, ds.attributes.icon))
                    .block();

            this.api.deviceSet.deleteDeviceSet(deviceSet.id)
                    .delayElement(Duration.ofSeconds(1))
                    .block();
            Thread.sleep(1000); // wait 1s to take effect
            deviceSet = this.getDeviceSet(deviceSet.id)
                    .doOnError(e -> log.info("Device-Set deleted: {}", e.getMessage()))
                    .doOnSuccess(ds -> log.error("Deleted Device-Set {} is still available: name={}, icon={}", ds.id, ds.attributes.name, ds.attributes.icon))
                    .onErrorResume(e -> Mono.empty())
                    .block();

        };
    }

    private Mono<DeviceSet> getDeviceSet(final String id) {
        return Mono.from(this.api.home()
                .flatMapIterable(h -> h.deviceSets)
                .filter(ds -> Objects.equals(ds.id, id)))
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Device-Set with id %s not found", id))));
    }

    public static void main(String[] args) {
        SpringApplication.run(DeviceSets.class, args).close();
    }


}
