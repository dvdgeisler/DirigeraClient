package de.dvdgeisler.iot.dirigera.client.examples.lightmonitor;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.light.LightDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;

/**
 * Monitor light status
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
@EnableScheduling
public class LightMonitor {
    private final static Logger log = LoggerFactory.getLogger(LightMonitor.class);

    private final DirigeraApi api;

    public LightMonitor(final DirigeraApi api) {
        this.api = api;
    }

    @Scheduled(fixedRate = 1000)
    public void monitor() {
        this.api.device.light.all()
                .flatMapMany(Flux::fromIterable)
                .filter(d -> d.deviceType == DeviceType.LIGHT)
                .cast(LightDevice.class)
                .doOnNext(this::printLightState)
                .blockLast();
    }

    private void printLightState(final LightDevice sensor) {
        log.info("{}: lightLevel={}, isOn={}, isReachable={}",
                sensor.attributes.state.customName != null && !sensor.attributes.state.customName.isBlank() ? sensor.attributes.state.customName : sensor.id,
                sensor.attributes.state.lightLevel,
                sensor.attributes.state.isOn,
                sensor.isReachable);
    }

    public static void main(String[] args) {
        SpringApplication.run(LightMonitor.class, args);
    }


}
