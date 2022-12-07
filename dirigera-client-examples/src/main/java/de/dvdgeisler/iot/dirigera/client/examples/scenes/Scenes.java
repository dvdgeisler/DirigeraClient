package de.dvdgeisler.iot.dirigera.client.examples.scenes;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

/**
 * Create, edit, and delete a scene
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class Scenes {
    private final static Logger log = LoggerFactory.getLogger(Scenes.class);
    private final DirigeraApi api;

    public Scenes(final DirigeraApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            Scene scene;

            scene = this.api.scene.create("My-First-Scene", "Icon")
                    .doOnSuccess(s -> log.info("Created Scene {}: name={}, icon={}", s.id, s.attributes.info.name, s.attributes.info.icon))
                    .block();

            scene = this.api.scene.update(scene, "Updated Scene name", "Icon")
                    .doOnSuccess(ds -> log.info("Updated Scene {}: name={}, icon={}", ds.id, ds.attributes.info.name, ds.attributes.info.icon))
                    .block();

            this.api.scene.delete(scene)
                    .thenReturn(scene)
                    .cache()
                    .flatMap(this.api.scene::refresh)
                    .repeat(10)// repeat 10 times as deleting may take some time
                    .last()
                    .doOnError(e -> log.info("Scene deleted: {}", e.getMessage()))
                    .doOnSuccess(s -> log.error("Deleted Scene {} is still available: name={}, icon={}", s.id, s.attributes.info.name, s.attributes.info.icon))
                    .onErrorResume(e -> Mono.empty())
                    .block();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Scenes.class, args).close();
    }


}
