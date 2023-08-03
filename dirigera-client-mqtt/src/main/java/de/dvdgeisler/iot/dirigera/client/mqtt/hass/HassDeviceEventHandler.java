package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.events.DeviceAddedEvent;
import de.dvdgeisler.iot.dirigera.client.api.model.events.DeviceConfigurationChangedEvent;
import de.dvdgeisler.iot.dirigera.client.api.model.events.DeviceRemovedEvent;
import de.dvdgeisler.iot.dirigera.client.api.model.events.DeviceStateChangedEvent;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttDeviceEventHandler;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class HassDeviceEventHandler<D extends Device> extends MqttDeviceEventHandler<D> {
    private final static Logger log = LoggerFactory.getLogger(HassDeviceEventHandler.class);

    protected final static String TOPIC_CONFIG = "config";
    protected final static String TOPIC_STATE = "state";
    protected final static String TOPIC_SET = "set";
    protected final static String TOPIC_AVAILABILITY = "availability";
    protected final static String TOPIC_REMOVE = "remove";
    private String gatewayId;
    private final String topicPrefix;

    public HassDeviceEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            final Class<D> deviceType,
            final String topicPrefix,
            final ObjectMapper objectMapper) {
        super(mqtt, api, deviceType, objectMapper);
        this.topicPrefix = topicPrefix;
    }

    protected String getTopic(final D device, final String component, final String... suffix) {
        return Stream.concat(
                Stream.of(this.topicPrefix, component, this.getGatewayId(), device.id),
                Arrays.stream(suffix)
        ).collect(Collectors.joining("/"));
    }

    protected String getGatewayId() {
        synchronized (this) {
            if (this.gatewayId == null) {
                this.gatewayId = this.getApi().getHome()
                        .map(h -> h.hub)
                        .map(h -> h.id)
                        .block();
            }
        }
        return this.gatewayId;
    }

    public static <D extends Device> String getDefaultName(final D device) {
        return Optional.of(device)
                .map(d->d.attributes)
                .map(a->a.state)
                .map(s->s.customName)
                .filter(n -> !n.isBlank())
                .orElse(Optional.of(device)
                        .map(d->d.attributes)
                        .map(a->a.model)
                        .filter(n -> !n.isBlank())
                        // MQTT entities aren't allowed to have the same Device ID as Entity ID in HASS from version 2024.2.0
                        // As a fallback to not having a custom name set, we add 'dirigera_' in front of the device ID
                        .orElse(String.format("%s_%s", "dirigera_", device.id)));
    }

    public de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device getDeviceConfig(final D device) {
        return new de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device(
                Optional.of(device).map(d->d.attributes).map(a->a.serialNumber).stream().toList(),
                Optional.of(device).map(d->d.attributes).map(a->a.manufacturer).orElse(null),
                Optional.of(device).map(d->d.attributes).map(a->a.model).orElse(null),
                Optional.of(device).map(d->d.attributes).map(a->a.hardwareVersion).orElse(null),
                Optional.of(device).map(d->d.attributes).map(a->a.firmwareVersion).orElse(null),
                getDefaultName(device),
                this.getGatewayId());
    }

    public static <D extends Device> boolean canReceive(final D device, final String... capabilities) {
        return Optional.of(device)
                .map(d->d.capabilities)
                .map(d->d.canReceive)
                .filter(d-> Arrays.stream(capabilities).anyMatch(d::contains))
                .isPresent();
    }

    public static <D extends Device> Optional<DeviceAvailabilityState> getAvailability(final D device) {
        return Optional.of(device)
                .map(d->d.isReachable)
                .map(d-> d ? DeviceAvailabilityState.ONLINE : DeviceAvailabilityState.OFFLINE);
    }

    @Override
    protected void onDeviceEvent(final DeviceAddedEvent<D> event) {
        log.debug("Received device added event: {}", this.toJSON(event.data));

        this.onDeviceCreated(event.data);
    }

    @Override
    protected void onDeviceEvent(final DeviceConfigurationChangedEvent<D> event) {
        log.debug("Received device configuration changed event: {}", this.toJSON(event.data));

        this.onDeviceStateChanged(event.data);
    }

    @Override
    protected void onDeviceEvent(final DeviceStateChangedEvent<D> event) {
        log.debug("Received device state changed event: {}", this.toJSON(event.data));

        this.onDeviceStateChanged(event.data);
    }

    @Override
    protected void onDeviceEvent(final DeviceRemovedEvent<D> event) {
        log.debug("Received device removed event: {}", this.toJSON(event.data));
        this.onDeviceRemoved(event.data);
    }

    protected abstract void onDeviceCreated(final D device);

    protected abstract void onDeviceStateChanged(final D device);

    protected abstract void onDeviceRemoved(final D device);
}
