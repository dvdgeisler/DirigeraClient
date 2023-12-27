package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.AirPurifierDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier.AirPurifierDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier.AirPurifierFanMode;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.airpurifier.AirPurifierConfig;
import java.util.Optional;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HassAirPurifierEventHandler extends HassDeviceEventHandler<AirPurifierDevice> {
    private final static String HASS_COMPONENT = "fan";

    private final AirPurifierDeviceApi api;

    public HassAirPurifierEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}")
            final String topicPrefix,
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

        config = new AirPurifierConfig();
        config.unique_id = config.object_id = device.id;
        config.name = getDefaultName(device);
        config.device = this.getDeviceConfig(device);
        config.command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SET);
        config.state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_STATE);
        config.payload_off = this.toJSON(AirPurifierFanMode.OFF);
        config.payload_on = this.toJSON(AirPurifierFanMode.ON);
        config.payload_low = this.toJSON(AirPurifierFanMode.LOW);
        config.payload_medium = this.toJSON(AirPurifierFanMode.MEDIUM);
        config.payload_high = this.toJSON(AirPurifierFanMode.HIGH);
        config.payload_auto = this.toJSON(AirPurifierFanMode.AUTO);
        config.state_off =  this.toJSON(AirPurifierFanMode.OFF);
        config.state_on =  this.toJSON(AirPurifierFanMode.ON);
        config.state_low =  this.toJSON(AirPurifierFanMode.LOW);
        config.state_medium = this.toJSON(AirPurifierFanMode.MEDIUM);
        config.state_high = this.toJSON(AirPurifierFanMode.HIGH);
        config.state_auto = this.toJSON(AirPurifierFanMode.AUTO);
        config.value_template = "{{value}}";

        config.availability = new DeviceAvailability();
        config.availability.topic = this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY);
        config.availability.payload_available = DeviceAvailabilityState.ONLINE.toString();
        config.availability.payload_not_available = DeviceAvailabilityState.OFFLINE.toString();
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_CONFIG), config);

        this.subscribe(
                this.getTopic(device, HASS_COMPONENT, TOPIC_SET),
                AirPurifierFanMode.class,
                status -> this.setDeviceStatus(device, status));

        this.onDeviceStateChanged(device);
    }

    protected void setDeviceStatus(final AirPurifierDevice device, final AirPurifierFanMode state) {
        this.api.setFanMode(device, state).block();
    }

    @Override
    protected void onDeviceStateChanged(final AirPurifierDevice device) {
        getState(device).ifPresent(state ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_STATE), state));
        getAvailability(device).ifPresent(s ->
                this.publishString(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s.toString()));
    }

    @Override
    protected void onDeviceRemoved(final AirPurifierDevice device) {
        this.publishString(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE.toString());
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
        this.unsubscribe(this.getTopic(device, HASS_COMPONENT, TOPIC_SET));
    }

    public static Optional<AirPurifierFanMode> getState(final AirPurifierDevice device) {
        return Optional.of(device)
                .map(d->d.attributes)
                .map(a->a.state)
                .map(s->s.fanMode);
    }
}
