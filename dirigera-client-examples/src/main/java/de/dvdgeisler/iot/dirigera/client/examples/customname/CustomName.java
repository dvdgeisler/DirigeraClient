package de.dvdgeisler.iot.dirigera.client.examples.customname;

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
 * Lists all known devices
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class CustomName {
    private final static Logger log = LoggerFactory.getLogger(CustomName.class);


    @Bean
    public CommandLineRunner run(final DirigeraApi api) {
        return (String... args) -> {
            api.pairIfRequired().block(); // pair gateway if required

            api.room.all() // fetch all light devices from hub
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(room -> api.room.delete(room))
                    .blockLast();
            api.scene.all() // fetch all light devices from hub
                    .flatMapMany(Flux::fromIterable)
                    .flatMap(scene -> api.scene.delete(scene))
                    .blockLast();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomName.class, args).close();
    }


}
