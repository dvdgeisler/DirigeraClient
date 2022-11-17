package de.dvdgeisler.iot.dirigera.client.mqtt;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.LightEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class, MqttBridge.class})
public class DirigeraClientMqttApplication {
    private final static Logger log = LoggerFactory.getLogger(DirigeraClientMqttApplication.class);

    final MqttBridge mqttBridge;
    final HashMap<String, Device> devices;

    public DirigeraClientMqttApplication(final MqttBridge mqttBridge) {
        this.mqttBridge = mqttBridge;
        this.devices = new HashMap<>();
    }

    @Bean
    public CommandLineRunner run(final DirigeraApi api, final LightEventHandler lightMessageFactory) {
        return (String... args) -> {

            api.pairIfRequired().block();

            this.mqttBridge.registerEventHandler(LightDevice.class, lightMessageFactory);

            api.device.all()
                    .flatMapMany(Flux::fromIterable)
                    .doOnNext(device -> {
                        if(!this.devices.containsKey(device.id))
                            this.mqttBridge.addDevice(device);
                    })
                    .doOnNext(this.mqttBridge::updateDevice)
                    .doOnNext(device -> this.devices.put(device.id, device))
                    .collectMap(device -> device.id)
                    .map(devices -> this.devices
                                .entrySet()
                                .stream()
                                .filter(deviceEntry -> !devices.containsKey(deviceEntry.getKey()))
                                .map(Map.Entry::getValue)
                                .toList())
                    .flatMapMany(Flux::fromIterable)
                    .doOnNext(this.mqttBridge::removeDevice)
                    .doOnNext(device -> this.devices.remove(device.id))
                    .collectList()
                    .delayElement(Duration.ofMillis(100))
                    .repeat()
                    .blockLast();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DirigeraClientMqttApplication.class, args).close();
    }

}
