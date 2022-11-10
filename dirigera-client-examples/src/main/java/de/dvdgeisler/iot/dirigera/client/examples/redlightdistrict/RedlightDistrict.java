package de.dvdgeisler.iot.dirigera.client.examples.redlightdistrict;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;

import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightState.*;

/**
 * Iterates through all devices and turns all lights on and to red.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class RedlightDistrict {
    private final static Logger log = LoggerFactory.getLogger(RedlightDistrict.class);

    @Bean
    public CommandLineRunner run(final DirigeraClientApi api) {
        return (String... args) -> {
            api.oauth.pairIfRequired().block();

            api.device.devices() // fetch all devices from hub
                    .flatMapMany(Flux::fromIterable)
                    .filter(d -> d.deviceType == DeviceType.LIGHT) // filter by light devices
                    .cast(LightDevice.class)
                    .doOnNext(d -> log.info("Found light '{}': isOn={}, hue={}, saturation={}, lightLevel={}",
                            d.attributes.state.customName,
                            d.attributes.state.isOn,
                            d.attributes.state.color.hue,
                            d.attributes.state.color.saturation,
                            d.attributes.state.lightLevel))
                    .flatMap(d -> api.device.editDevice(d.id, List.of(LIGHT_ON, LIGHT_LEVEL_100, LIGHT_COLOR_RED))) // edit device state
                    .blockLast();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RedlightDistrict.class, args).close();
    }


}
