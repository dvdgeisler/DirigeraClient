package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridge;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridgeMessage;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.motionsensor.MotionSensorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState.OFF;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState.ON;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceUtils.*;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.motionsensor.MotionSensorUtils.getState;

@Component
public class MotionSensorEventHandler extends HassEventHandler<MotionSensorDevice, DeviceState> {
    private final static Logger log = LoggerFactory.getLogger(MotionSensorEventHandler.class);
    private final static String HASS_COMPONENT = "binary_sensor";
    private final static String TOPIC_CONFIG = "config";
    private final static String TOPIC_STATE = "state";
    private final static String TOPIC_AVAILABILITY = "availability";
    private final static String TOPIC_REMOVE = "remove";

    public MotionSensorEventHandler(
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}") final String prefix,
            final ObjectMapper objectMapper) {
        super(prefix, HASS_COMPONENT, objectMapper, DeviceState.class);
    }

    @Override
    public void setDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final MotionSensorDevice device,
            final DeviceState status) {
        log.error("Device cannot be set");
    }


    @Override
    public Stream<MqttBridgeMessage<MotionSensorDevice>> addDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final MotionSensorDevice device) {

        return Stream.of(this.build(mqtt, device, TOPIC_CONFIG,
                new MotionSensorConfig(
                        device.id,
                        device.id,
                        getDefaultName(device),
                        getDeviceConfig(mqtt, device),
                        this.toJSON(ON),
                        this.toJSON(OFF),
                        this.topic(mqtt, device, TOPIC_STATE),
                        this.getDeviceAvailabilityConfig(mqtt, device, TOPIC_AVAILABILITY))));
    }

    @Override
    public Stream<MqttBridgeMessage<MotionSensorDevice>> updateDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final MotionSensorDevice device) {
        return Stream.of(
                this.build(mqtt, device, TOPIC_STATE, getState(device)),
                this.build(mqtt, device, TOPIC_AVAILABILITY, getAvailability(device)));
    }

    @Override
    public Stream<MqttBridgeMessage<MotionSensorDevice>> removeDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final MotionSensorDevice device) {
        return Stream.of(this.build(mqtt, device, TOPIC_REMOVE, null));
    }
}
