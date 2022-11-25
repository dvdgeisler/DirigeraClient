package de.dvdgeisler.iot.dirigera.client.examples.scenetriggers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Use Controllers and Sensors to trigger Scenes
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
public class SceneTriggers {
    private final static Logger log = LoggerFactory.getLogger(SceneTriggers.class);
    private final DirigeraApi dapi;
    private final ClientApi capi;
    private final ObjectMapper json;

    public SceneTriggers(final DirigeraApi dapi, final ClientApi capi, final ObjectMapper json) {
        this.dapi = dapi;
        this.capi = capi;
        this.json = json;
    }

    @Bean
    public CommandLineRunner run() {
        return (String... args) -> {
            List<Scene> scenes;
            this.dapi.pairIfRequired().block();


            scenes = new ArrayList<>();


            try {
                this.dapi.device.controller.light // create dummy scenes for light controller
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                this.dapi.device.controller.shortcut // create dummy scenes for shortcut controller
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                this.dapi.device.controller.sound // create dummy scenes for sound controller
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                this.dapi.device.motionSensor // create dummy scenes for motion sensors
                        .all()
                        .block()
                        .stream()
                        .flatMap(this::createDummyScenes)
                        .forEach(scenes::add);
                log.info("Press button of any connected controller");
                this.capi.websocket(this::logButtonPress)
                        .block(Duration.ofSeconds(60));
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            // clean up
            scenes.stream()
                    .peek(scene -> log.info("Delete scene {}", scene.attributes.info.name))
                    .map(this.dapi.scene::delete)
                    .forEach(Mono::block);
        };
    }

    /**
     * Create button for dedicaterd trigger for specific button
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
     * @param device
     * @param button
     * @return
     */
    private Scene createDummyScene(final Device device, final int button) {
        final Scene scene;
        final String name;

        name = String.format("%s button %d", device.attributes.state.customName, button);

        scene = this.dapi.scene.create(name, "Icon")
                .doOnSuccess(s -> log.info("Created Scene {}: name={}, icon={}", s.id, s.attributes.info.name, s.attributes.info.icon))
                .block(); // create dummy scene

        return this.dapi.scene.setTrigger(scene, List.of(
                new SceneTriggerApp(null, null, null, null),
                createTrigger(device, button))
        ).block();
    }

    /**
     * Create dummy scenes for all possible buttons
     * @param device
     * @return
     */
    private Stream<Scene> createDummyScenes(final Device device) {
        return Stream.of(
                this.createDummyScene(device, 0),
                this.createDummyScene(device, 1),
                this.createDummyScene(device, 2)); // button 3 raises an error on the dirigera
    }

    /**
     * Log the button press
     * @param s
     */
    private void logButtonPress(final String s) {
        final Map json, data, info;
        final String type;

        try {
            json = this.json.readValue(s, Map.class);
            type = json.get("type").toString();
            if(!Objects.equals(type, "sceneUpdated")) {
                log.info("Received: {}", s);
                return;
            }
            data = (Map) json.get("data");
            info = (Map) data.get("info");
            log.info("Received: {}", info.get("name"));
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SceneTriggers.class, args).close();
    }


}
