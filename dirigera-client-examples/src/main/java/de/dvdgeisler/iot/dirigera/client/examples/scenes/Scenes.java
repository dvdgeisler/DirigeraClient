package de.dvdgeisler.iot.dirigera.client.examples.scenes;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * Create, edit, and delete a scene
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraClientApi.class})
public class Scenes {
    private final static Logger log = LoggerFactory.getLogger(Scenes.class);
    private final DirigeraClientApi api;

    public Scenes(final DirigeraClientApi api) {
        this.api = api;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            Scene scene;
            this.api.oauth.pairIfRequired().block();

            scene = this.api.scene.createScene(
                    new SceneAttributes(new SceneInfo("My-First-Scene", "Icon"), List.of(), List.of()))
                    .delayElement(Duration.ofSeconds(1))
                    .map(i -> i.id)
                    .flatMap(this.api.scene::getScene)
                    .switchIfEmpty(Mono.error(new RuntimeException("Created Scene not found")))
                    .doOnSuccess(r -> log.info("Created Scene {}: name={}, icon={}", r.id, r.attributes.info.name, r.attributes.info.icon))
                    .block();

            scene.attributes.info.name = "Updated Scene name";
            scene.attributes.actions = List.of();   // actions and triggers are not
            scene.attributes.triggers = List.of();  // working right now
            this.api.scene.updateScene(scene.id, scene.attributes)
                    .block();
            Thread.sleep(1000); // wait 1s to take effect
            scene = this.api.scene.getScene(scene.id)
                    .switchIfEmpty(Mono.error(new RuntimeException("Created Room not found")))
                    .doOnSuccess(ds -> log.info("Updated Scene {}: name={}, icon={}", ds.id, ds.attributes.info.name, ds.attributes.info.icon))
                    .block();

            this.api.scene.deleteScene(scene.id)
                    .delayElement(Duration.ofSeconds(1))
                    .block();
            Thread.sleep(1000); // wait 1s to take effect
            scene = this.api.scene.getScene(scene.id)
                    .doOnError(e -> log.info("Scene deleted: {}", e.getMessage()))
                    .doOnSuccess(ds -> log.error("Deleted Scene {} is still available: name={}, icon={}", ds.id, ds.attributes.info.name, ds.attributes.info.icon))
                    .onErrorResume(e -> Mono.empty())
                    .block();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Scenes.class, args).close();
    }


}
