package de.dvdgeisler.iot.dirigera.client.examples.rooms;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

/**
 * Create, edit, and delete a room
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class Rooms {
    private final static Logger log = LoggerFactory.getLogger(Rooms.class);
    private final DirigeraApi api;

    public Rooms(final DirigeraApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            Room room;

            room = this.api.room.create("My-First-Room", "Icon", "Color")
                    .doOnSuccess(r -> log.info("Created Room {}: name={}, icon={}, color={}", r.id, r.attributes.name, r.attributes.icon, r.attributes.color))
                    .block();

            room = this.api.room.update(room, "Updated Room name", "Icon", "Color")
                    .doOnSuccess(r -> log.info("Updated Room {}: name={}, icon={}, color={}", r.id, r.attributes.name, r.attributes.icon, r.attributes.color))
                    .block();

            this.api.room.delete(room)
                    .thenReturn(room)
                    .cache()
                    .flatMap(this.api.room::refresh)
                    .repeat(10)// repeat 10 times as deleting may take some time
                    .last()
                    .doOnError(e -> log.info("Room deleted: {}", e.getMessage()))
                    .doOnSuccess(ds -> log.error("Deleted Room {} is still available: name={}, icon={}", ds.id, ds.attributes.name, ds.attributes.icon))
                    .onErrorResume(e -> Mono.empty())
                    .block();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Rooms.class, args).close();
    }


}
