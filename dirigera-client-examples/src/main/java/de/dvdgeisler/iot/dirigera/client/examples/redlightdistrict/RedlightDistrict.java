package de.dvdgeisler.iot.dirigera.client.examples.redlightdistrict;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;

/**
 * Iterates through all devices and turns all lights on and to red.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class RedlightDistrict {
    private final static Logger log = LoggerFactory.getLogger(RedlightDistrict.class);

    @Bean
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
            api.pairIfRequired().block();

            api.device.light.all()
                    .flatMapMany(Flux::fromIterable)
                    .doOnNext(d -> log.info("Found light '{}': isOn={}, hue={}, saturation={}, lightLevel={}",
                            d.attributes.state.customName,
                            d.attributes.state.isOn,
                            d.attributes.state.color.hue,
                            d.attributes.state.color.saturation,
                            d.attributes.state.lightLevel))
                    .flatMap(d -> api.device.light.turnOn(d))
                    .flatMap(d -> api.device.light.setLevel(d, 100))
                    .flatMap(d -> api.device.light.setColor(d, 360.0f, 1.0f))
                    .blockLast();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RedlightDistrict.class, args).close();
    }
}
