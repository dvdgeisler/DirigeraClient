package de.dvdgeisler.iot.dirigera.client.examples.airquality;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor.EnvironmentSensorDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;

/**
 * Monitor air quality
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {DirigeraApi.class})
@EnableScheduling
public class AirQualityMonitor {
    private final static Logger log = LoggerFactory.getLogger(AirQualityMonitor.class);

    private final DirigeraApi api;

    public AirQualityMonitor(final DirigeraApi api) {
        this.api = api;
    }

    @Scheduled(fixedRate = 1000)
    public void monitor() {
        this.api.device.environmentSensor.all()
                .flatMapMany(Flux::fromIterable)
                .filter(d -> d.deviceType == DeviceType.ENVIRONMENT_SENSOR)
                .cast(EnvironmentSensorDevice.class)
                .doOnNext(this::printAirQualityData)
                .blockLast();
    }

    private void printAirQualityData(final EnvironmentSensorDevice sensor) {
        log.info("{}: Temperature={}, RelativeHumidity={}, PM25={}, VOC={}, isReachable={}",
                sensor.attributes.state.customName != null && !sensor.attributes.state.customName.isBlank() ? sensor.attributes.state.customName : sensor.id,
                sensor.attributes.currentTemperature,
                sensor.attributes.currentRH,
                sensor.attributes.currentPM25,
                sensor.attributes.vocIndex,
                sensor.isReachable);
    }

    public static void main(String[] args) {
        SpringApplication.run(AirQualityMonitor.class, args);
    }


}
