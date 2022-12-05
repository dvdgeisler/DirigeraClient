package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.BlindsDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blinds.BlindsDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blinds.BlindsState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.blinds.BlindsConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HassBlindsDeviceEventHandler extends HassDeviceEventHandler<BlindsDevice> {
    private final static String HASS_COMPONENT = "cover";
    private static final String TOPIC_POSITION = "position";

    private final BlindsDeviceApi api;

    public HassBlindsDeviceEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}")
            final String topicPrefix,
            final ObjectMapper objectMapper) {
        super(mqtt, api, BlindsDevice.class, topicPrefix, objectMapper);

        this.api = api.device.blinds;
        this.api.all()
                .block()
                .forEach(this::onDeviceCreated);
    }

    @Override
    protected void onDeviceCreated(final BlindsDevice device) {
        final BlindsConfig config;

        config = new BlindsConfig();
        config.unique_id = config.object_id = device.id;
        config.name = getDefaultName(device);
        config.device = this.getDeviceConfig(device);
        config.command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SET);
        config.state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_STATE);
        config.position_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_POSITION);
        config.payload_close = this.toJSON(BlindsState.DOWN);
        config.payload_open = this.toJSON(BlindsState.UP);
        config.payload_stop = this.toJSON(BlindsState.STOPPED);
        config.position_open = 0;
        config.position_closed = 100;
        config.state_closed =  this.toJSON(BlindsState.DOWN);
        //config.state_closing = this.toJSON(BlindsState.DOWN);
        config.state_stopped = this.toJSON(BlindsState.STOPPED);
        //config.state_opening = this.toJSON(BlindsState.UP);
        config.state_open =    this.toJSON(BlindsState.UP);
        config.value_template = "{{value}}";
        config.position_template = "{{value}}";

        config.availability = new DeviceAvailability();
        config.availability.topic = this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY);
        config.availability.payload_available = this.toJSON(DeviceAvailabilityState.ONLINE);
        config.availability.payload_not_available = this.toJSON(DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_CONFIG), config);

        this.subscribe(
                this.getTopic(device, HASS_COMPONENT, TOPIC_SET),
                BlindsState.class,
                status -> this.setDeviceStatus(device, status));

        this.onDeviceStateChanged(device);
    }

    protected void setDeviceStatus(final BlindsDevice device, final BlindsState state) {
        this.api.setState(device, state).block();
    }

    @Override
    protected void onDeviceStateChanged(final BlindsDevice device) {
        getState(device).ifPresent(state ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_STATE), state));
        getPosition(device).ifPresent(position ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_POSITION), position));
        getAvailability(device).ifPresent(s ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s));
    }

    @Override
    protected void onDeviceRemoved(final BlindsDevice device) {
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
        this.unsubscribe(this.getTopic(device, HASS_COMPONENT, TOPIC_SET));
    }

    public static Optional<BlindsState> getState(final BlindsDevice device) {
        return Optional.of(device)
                .map(d->d.attributes)
                .map(a->a.state)
                .map(s->s.blindsState);
    }

    public static Optional<Integer> getPosition(final BlindsDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.blindsCurrentLevel);
    }
}
