package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.MotionSensorDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.motionsensor.MotionSensorDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.outlet.OutletConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HassMotionSensorDeviceEventHandler extends HassDeviceEventHandler<MotionSensorDevice> {
    private final static String HASS_COMPONENT = "binary_sensor";

    private final MotionSensorDeviceApi api;

    public HassMotionSensorDeviceEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}")
            final String topicPrefix,
            final ObjectMapper objectMapper) {
        super(mqtt, api, MotionSensorDevice.class, topicPrefix, objectMapper);

        this.api = api.device.motionSensor;
        this.api.all()
                .block()
                .forEach(this::onDeviceCreated);
    }

    @Override
    protected void onDeviceCreated(final MotionSensorDevice device) {
        final OutletConfig config;

        config = new OutletConfig();
        config.unique_id = config.object_id = device.id;
        config.name = getDefaultName(device);
        config.device = this.getDeviceConfig(device);
        config.command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SET);
        config.state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_STATE);
        config.payload_on = config.state_on = this.toJSON(DeviceState.ON);
        config.payload_off = config.state_off = this.toJSON(DeviceState.OFF);

        config.availability = new DeviceAvailability();
        config.availability.topic = this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY);
        config.availability.payload_available = this.toJSON(DeviceAvailabilityState.ONLINE);
        config.availability.payload_not_available = this.toJSON(DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_CONFIG), config);

        this.onDeviceStateChanged(device);
    }

    @Override
    protected void onDeviceStateChanged(final MotionSensorDevice device) {
        getState(device).ifPresent(state ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_STATE), state));
        getAvailability(device).ifPresent(s ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s));
    }

    @Override
    protected void onDeviceRemoved(final MotionSensorDevice device) {
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
    }

    public static Optional<DeviceState> getState(final MotionSensorDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.isOn)
                .map(d -> d ? DeviceState.ON : DeviceState.OFF);
    }
}
