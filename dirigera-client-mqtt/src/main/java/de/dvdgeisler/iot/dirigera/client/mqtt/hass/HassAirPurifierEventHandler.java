package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.AirPurifierDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier.AirPurifierDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier.AirPurifierFanMode;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.airpurifier.AirPurifierConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HassAirPurifierEventHandler extends HassDeviceEventHandler<AirPurifierDevice> {
    private final static String HASS_COMPONENT = "fan";
    private final static String TOPIC_SPEED = "speed";
    private final static String TOPIC_PRESET = "preset";

    private final AirPurifierDeviceApi api;

    public HassAirPurifierEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}") final String topicPrefix,
            final ObjectMapper objectMapper) {
        super(mqtt, api, AirPurifierDevice.class, topicPrefix, objectMapper);

        this.api = api.device.airPurifier;
        this.api.all()
                .block()
                .forEach(this::onDeviceCreated);
    }

    @Override
    protected void onDeviceCreated(final AirPurifierDevice device) {
        final AirPurifierConfig config;
        final AirPurifierFanMode[] presetModes = new AirPurifierFanMode[]{AirPurifierFanMode.AUTO, AirPurifierFanMode.LOW, AirPurifierFanMode.MEDIUM, AirPurifierFanMode.HIGH};

        config = new AirPurifierConfig();
        config.unique_id = config.object_id = device.id;
        config.name = getDefaultName(device);
        config.device = this.getDeviceConfig(device);
        config.command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SET);
        config.percentage_state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SPEED);
        config.preset_mode_command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_PRESET, TOPIC_SET);
        config.preset_mode_state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_PRESET, TOPIC_STATE);
        config.state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_STATE);
        config.payload_off = this.toJSON(AirPurifierFanMode.OFF);
        config.payload_on = this.toJSON(AirPurifierFanMode.ON);
        config.preset_modes = this.toJSON(presetModes);
        config.state_off = this.toJSON(AirPurifierFanMode.OFF);
        config.state_on = this.toJSON(AirPurifierFanMode.ON);
        config.preset_mode_value_template = "{{value}}";
        config.percentage_value_template = "{{value}}";
        config.value_template = "{{value}}";

        config.availability = new DeviceAvailability();
        config.availability.topic = this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY);
        config.availability.payload_available = this.toJSON(DeviceAvailabilityState.ONLINE);
        config.availability.payload_not_available = this.toJSON(DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_CONFIG), config);

        this.subscribe(
                this.getTopic(device, HASS_COMPONENT, TOPIC_SET),
                AirPurifierFanMode.class,
                status -> this.setDeviceStatus(device, status));
        this.subscribe(
                this.getTopic(device, HASS_COMPONENT, TOPIC_PRESET, TOPIC_SET),
                AirPurifierFanMode.class,
                status -> this.setPresetMode(device, status));

        this.onDeviceStateChanged(device);
    }

    protected void setDeviceStatus(final AirPurifierDevice device, final AirPurifierFanMode state) {
        this.api.setFanMode(device, state).block();
    }

    protected void setPresetMode(final AirPurifierDevice device, final AirPurifierFanMode state) {
        this.api.setFanMode(device, state).block();
    }

    @Override
    protected void onDeviceStateChanged(final AirPurifierDevice device) {
        getState(device).ifPresent(state ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_STATE), state));
        getPreset(device).ifPresent(preset ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_PRESET, TOPIC_STATE), preset));
        getSpeed(device).ifPresent(speed ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_SPEED), speed));
        getAvailability(device).ifPresent(s ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s));
    }

    @Override
    protected void onDeviceRemoved(final AirPurifierDevice device) {
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
        this.unsubscribe(this.getTopic(device, HASS_COMPONENT, TOPIC_SET));
    }

    public static Optional<AirPurifierFanMode> getState(final AirPurifierDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(a -> a.state)
                .map(s -> switch (s.fanMode) {
                    case LOW, MEDIUM, HIGH, AUTO, ON -> AirPurifierFanMode.ON;
                    case OFF -> AirPurifierFanMode.OFF;
                });
    }

    public static Optional<AirPurifierFanMode> getPreset(final AirPurifierDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(a -> a.state)
                .filter(a -> !AirPurifierFanMode.OFF.equals(a.fanMode) && !AirPurifierFanMode.ON.equals(a.fanMode))
                .map(s -> s.fanMode);
    }

    public static Optional<Integer> getSpeed(final AirPurifierDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(a -> a.state)
                .map(s -> s.motorState);
    }
}
