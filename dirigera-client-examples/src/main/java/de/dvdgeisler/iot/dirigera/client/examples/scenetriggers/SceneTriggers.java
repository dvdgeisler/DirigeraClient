package de.dvdgeisler.iot.dirigera.client.examples.scenetriggers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.Scene;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerApp;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerController;
import de.dvdgeisler.iot.dirigera.client.api.model.scene.SceneTriggerControllerTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

/**
 * Use Controllers and Sensors to trigger Scenes
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class SceneTriggers {
    private final static Logger log = LoggerFactory.getLogger(SceneTriggers.class);
    private final DirigeraApi api;
    private final ObjectMapper json;

    public SceneTriggers(final DirigeraApi api, final ObjectMapper json) {
        this.api = api;
        this.json = json;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            List<Scene> scenes;

            scenes = new ArrayList<>();

            try {
                this.api.device.controller.light // create dummy scenes for light controller
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                this.api.device.controller.shortcut // create dummy scenes for shortcut controller
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                this.api.device.controller.sound // create dummy scenes for sound controller
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                this.api.device.motionSensor // create dummy scenes for motion sensors
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                log.info("Press button of any connected controller");
                this.api.scene.websocket(event -> {
                    try {
                        log.info("Received: {}\n{}", event.data.attributes.info.name, json.writeValueAsString(event));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

                Thread.sleep(Duration.ofSeconds(60).toMillis());
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            // clean up
            scenes.stream()
                    .peek(scene -> log.info("Delete scene {}",
                            Optional.of(scene)
                                    .map(s -> s.attributes)
                                    .map(a -> a.info)
                                    .map(i -> i.name)))
                    .map(this.api.scene::delete)
                    .forEach(Mono::block);
            this.api.websocket.stop();

        };
    }

    /**
     * Create button for dedicated trigger for specific button
     *
     * @param device
     * @param buttonIndex
     * @return
     */
    private static SceneTriggerController createTrigger(final Device device, final int buttonIndex) {
        return new SceneTriggerController(
                null, false, null, null,
                new SceneTriggerControllerTrigger(
                        null,
                        DeviceType.SHORTCUT_CONTROLLER,
                        buttonIndex,
                        device.id));
    }

    /**
     * Create a dummy scene to catch the given button press
     *
     * @param device
     * @param button
     * @return
     */
    private Scene createDummyScene(final Device device, final int button) {
        final Scene scene;
        final String name;

        name = String.format("%s button %d", device.attributes.state.customName, button);

        scene = this.api.scene.create(name, "Icon")
                .doOnSuccess(s -> log.info("Created Scene {}: name={}, icon={}", s.id, s.attributes.info.name, s.attributes.info.icon))
                .block(); // create dummy scene

        return this.api.scene.setTrigger(scene, List.of(
                new SceneTriggerApp(null, null, null, null),
                createTrigger(device, button))
        ).block();
    }

    /**
     * Create dummy scenes for all possible buttons
     *
     * @param device
     * @return
     */
    private Stream<Scene> createDummyScenes(final Device device) {
        return Stream.of(
                this.createDummyScene(device, 0),
                this.createDummyScene(device, 1),
                this.createDummyScene(device, 2)); // button 3 raises an error on the dirigera
    }

    public static void main(String[] args) {
        SpringApplication.run(SceneTriggers.class, args).close();
    }


}
