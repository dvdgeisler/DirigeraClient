package de.dvdgeisler.iot.dirigera.client.examples.discolights;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.light.LightState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.device.light.LightState.*;

/**
 * Randomly change color and light level
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
@EnableScheduling
public class DiscoLights {
    private final static Logger log = LoggerFactory.getLogger(DiscoLights.class);

    private final static List<List<LightState>> states = new ArrayList<>() {{
        for (LightState level: List.of(LIGHT_LEVEL_30, LIGHT_LEVEL_70, LIGHT_LEVEL_100))
            for (LightState color: List.of(LIGHT_COLOR_RED, LIGHT_COLOR_GREEN, LIGHT_COLOR_BLUE,LIGHT_COLOR_WHITE))
                this.add(List.of(LIGHT_ON, level, color));
    }};

    private final DirigeraClientApi api;

    public DiscoLights(final DirigeraClientApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            if (!this.api.hasAccessToken())
                this.api.pair().block();
        };
    }

    @Scheduled(fixedDelay = 2000)
    public void changeState() {
        if(!this.api.hasAccessToken())
            return;

        this.api.devices() // fetch all devices from hub
                .flatMapMany(Flux::fromIterable)
                .filter(d -> d.deviceType == DeviceType.LIGHT) // filter by light devices
                .cast(LightDevice.class)
                .flatMap(d -> api.editDevice(d.id, this.randomState())) // edit device state
                .blockLast();
    }

    public static void main(String[] args) {
        SpringApplication.run(DiscoLights.class, args);
    }

    private List<LightState> randomState() {
        return states.get(ThreadLocalRandom.current().nextInt(0, states.size()));
    }

}
