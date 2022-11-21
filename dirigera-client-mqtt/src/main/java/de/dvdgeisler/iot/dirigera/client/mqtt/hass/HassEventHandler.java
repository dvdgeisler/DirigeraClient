package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridge;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridgeMessage;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttEventHandler;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightStatus;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState.OFFLINE;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState.ONLINE;
import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class HassEventHandler<_Device extends de.dvdgeisler.iot.dirigera.client.api.model.device.Device, _DeviceState> implements MqttEventHandler<_Device> {
    private final static Logger log = LoggerFactory.getLogger(HassEventHandler.class);

    private final String topicPrefix;
    private final String hassComponent;
    private final ObjectMapper objectMapper;
    private final Class<_DeviceState> deviceStateClass;

    public HassEventHandler(
            final String topicPrefix,
            final String hassComponent,
            final ObjectMapper objectMapper,
            final Class<_DeviceState> deviceStateClass) {
        this.topicPrefix = topicPrefix;
        this.objectMapper = objectMapper;
        this.hassComponent = hassComponent;
        this.deviceStateClass = deviceStateClass;
    }

    protected String topic(final MqttBridge mqtt, final _Device device, final String... suffix) {
        return Stream.concat(
                        Stream.of(this.topicPrefix, this.hassComponent, mqtt.getClientId(), device.id),
                        Stream.of(suffix))
                .collect(Collectors.joining("/"));
    }

    protected <T> MqttBridgeMessage<_Device> build(final MqttBridge mqtt, final _Device device, final String topic, final T payload) {
        try {
            return new MqttBridgeMessage<>(
                    device,
                    this,
                    this.topic(mqtt, device, topic),
                    new MqttMessage(this.objectMapper.writeValueAsBytes(payload)));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    protected void subscribe(final MqttBridge mqtt, final DirigeraApi api, final _Device device) {
        try {
            mqtt.subscribe(this.topic(mqtt, device, "set"), (topic, message) -> {
                final _DeviceState state;

                try {
                    log.debug("Received state command: {}", new String(message.getPayload()));
                    state = HassEventHandler.this.fromJSON(message.getPayload(), this.deviceStateClass);
                    this.setDevice(mqtt, api, device, state);

                } catch (final Throwable e) {
                    log.error(e.getMessage());
                }
            });
        } catch (final MqttException e) {
            log.error(e.getMessage());
        }
    }

    public abstract void setDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final _Device device,
            final _DeviceState state);

    protected <T> String toJSON(final T object) {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    protected <T> T fromJSON(final String s, final Class<T> type) {
        String es;
        if (type.isEnum()) {
            for (final T e : type.getEnumConstants()) {
                es = Optional.of(e)
                        .map(c -> c.getClass())
                        .map(c -> c.getAnnotation(JsonProperty.class))
                        .map(JsonProperty::value)
                        .orElse(String.valueOf(e));
                if (Objects.equals(es, s))
                    return e;
            }
        }
        try {
            return this.objectMapper.readValue(s, type);
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    protected <T> T fromJSON(final byte[] s, final Class<T> type) {
        return this.fromJSON(new String(s), type);
    }

    protected DeviceAvailability getDeviceAvailabilityConfig(final MqttBridge mqtt, final _Device device, final String... topic) {
        return new DeviceAvailability(
                this.topic(mqtt, device, topic),
                this.toJSON(ONLINE),
                this.toJSON(OFFLINE)
        );
    }
}
