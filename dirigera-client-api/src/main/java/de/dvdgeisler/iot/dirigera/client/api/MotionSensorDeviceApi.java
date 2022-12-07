package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;

public class MotionSensorDeviceApi extends ControllerDeviceApi<
        MotionSensorStateAttributes,
        MotionSensorAttributes,
        MotionSensorConfigurationAttributes,
        MotionSensorDevice> {

    public MotionSensorDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return super.isInstance(device) && device instanceof MotionSensorDevice;
    }

    public Mono<MotionSensorDevice> setOnDuration(final MotionSensorDevice device, final Duration duration) {
        final MotionSensorConfigurationAttributes attributes;

        attributes = new MotionSensorConfigurationAttributes();
        attributes.onDuration = Math.toIntExact(duration.toSeconds());

        return this.setConfigurationAttribute(device, attributes);
    }

    public Mono<MotionSensorDevice> enableSchedule(final MotionSensorDevice device) {
        final MotionSensorConfigurationAttributes attributes;

        attributes = new MotionSensorConfigurationAttributes();
        attributes.sensorConfig = new MotionSensorConfig(
                true,
                null,
                null
        );

        return this.setConfigurationAttribute(device, attributes);
    }

    public Mono<MotionSensorDevice> disableSchedule(final MotionSensorDevice device) {
        final MotionSensorConfigurationAttributes attributes;

        attributes = new MotionSensorConfigurationAttributes();
        attributes.sensorConfig = new MotionSensorConfig(
                false,
                null,
                null
        );

        return this.setConfigurationAttribute(device, attributes);
    }

    public Mono<MotionSensorDevice> setSchedule(final MotionSensorDevice device, final LocalTime from, final LocalTime to) {
        final MotionSensorConfigurationAttributes attributes;

        attributes = new MotionSensorConfigurationAttributes();
        attributes.sensorConfig = new MotionSensorConfig(
                null,
                null,
                new MotionSensorSchedule(
                        new MotionSensorScheduleEntry(from),
                        new MotionSensorScheduleEntry(to)
                )
        );

        return this.setConfigurationAttribute(device, attributes);
    }
}
