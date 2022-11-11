package de.dvdgeisler.iot.dirigera.client.examples.rooms;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Create, edit, and delete a room
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class Rooms {
    private final static Logger log = LoggerFactory.getLogger(Rooms.class);
    private final DirigeraClientApi api;

    public Rooms(final DirigeraClientApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            Room room;
            this.api.oauth.pairIfRequired().block();

            room = this.api.room.createRoom("My-First-Room", "Icon", "Color")
                    .delayElement(Duration.ofSeconds(1))
                    .map(i -> i.id)
                    .flatMap(this.api.room::getRoom)
                    .switchIfEmpty(Mono.error(new RuntimeException("Created Room not found")))
                    .doOnSuccess(r -> log.info("Created Room {}: name={}, icon={}, color={}", r.id, r.attributes.name, r.attributes.icon, r.attributes.color))
                    .block();

            room.attributes.name = "Updated Room name";
            this.api.room.updateRoom(room.id, room.attributes)
                    .block();
            Thread.sleep(1000); // wait 1s to take effect
            room = this.api.room.getRoom(room.id)
                    .switchIfEmpty(Mono.error(new RuntimeException("Created Room not found")))
                    .doOnSuccess(ds -> log.info("Updated Room {}: name={}, icon={}, color={}", ds.id, ds.attributes.name, ds.attributes.icon, ds.attributes.color))
                    .block();

            this.api.room.deleteRoom(room.id)
                    .delayElement(Duration.ofSeconds(1))
                    .block();
            Thread.sleep(1000); // wait 1s to take effect
            room = this.api.room.getRoom(room.id)
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
