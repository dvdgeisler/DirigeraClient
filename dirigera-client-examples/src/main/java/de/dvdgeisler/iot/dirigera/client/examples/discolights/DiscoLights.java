package de.dvdgeisler.iot.dirigera.client.examples.discolights;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Randomly change color and light level
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
@EnableScheduling
public class DiscoLights {

    private final DirigeraApi api;

    public DiscoLights(final DirigeraApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> this.api.pairIfRequired().block();
    }

    @Scheduled(fixedDelay = 500)
    public void changeState() {
        if(!this.api.isPaired())
            return;

        this.api.device.light.all() // fetch all light devices from hub
                .flatMapMany(Flux::fromIterable)
                .flatMap(device ->
                    Mono.just(device)
                            // set random color
                            .flatMap(d -> this.api.device.light.setColor(d, this.rnd(0.0f, 360.0f), this.rnd(0.2f, 1.0f)))
                            // set random light level
                            .flatMap(d -> this.api.device.light.setLevel(d, this.rnd(40, 100)))
                ).blockLast();
    }

    private int rnd(final int lower, final int upper) {
        return ThreadLocalRandom.current().nextInt(lower, upper);
    }

    private float rnd(final float lower, final float upper) {
        return ThreadLocalRandom.current().nextFloat(lower, upper);
    }

    public static void main(String[] args) {
        SpringApplication.run(DiscoLights.class, args);
    }

}
