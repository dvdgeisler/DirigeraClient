package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.OutletDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.outlet.OutletDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.outlet.OutletConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HassOutletDeviceEventHandler extends HassDeviceEventHandler<OutletDevice> {
    private final static String HASS_COMPONENT = "switch";

    private final OutletDeviceApi api;

    public HassOutletDeviceEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}")
            final String topicPrefix,
            final ObjectMapper objectMapper) {
        super(mqtt, api, OutletDevice.class, topicPrefix, objectMapper);

        this.api = api.device.outlet;
        this.api.all()
                .block()
                .forEach(this::onDeviceCreated);
    }

    @Override
    protected void onDeviceCreated(final OutletDevice device) {
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

        this.subscribe(
                this.getTopic(device, HASS_COMPONENT, TOPIC_SET),
                DeviceState.class,
                status -> this.setDeviceStatus(device, status));

        this.onDeviceStateChanged(device);
    }

    protected void setDeviceStatus(final OutletDevice device, final DeviceState status) {
        switch (status) {
            case ON -> this.api.turnOn(device).block();
            case OFF -> this.api.turnOff(device).block();
        }
    }

    @Override
    protected void onDeviceStateChanged(final OutletDevice device) {
        getState(device).ifPresent(state ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_STATE), state));
        getAvailability(device).ifPresent(s ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s));
    }

    @Override
    protected void onDeviceRemoved(final OutletDevice device) {
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
    }

    public static Optional<DeviceState> getState(final OutletDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.isOn)
                .map(d -> d ? DeviceState.ON : DeviceState.OFF);
    }
}
