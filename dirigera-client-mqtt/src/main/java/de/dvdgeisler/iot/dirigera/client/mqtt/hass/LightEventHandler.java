package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridge;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridgeMessage;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttEventHandler;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightColor;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightColorMode;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightConfig;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightStatus;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState.OFFLINE;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState.ONLINE;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
public class LightEventHandler implements MqttEventHandler<LightDevice> {
    private final static Logger log = LoggerFactory.getLogger(LightEventHandler.class);
    private final static String COMPONENT_PREFIX = "light";

    private final String prefix;
    private final ObjectMapper objectMapper;

    public LightEventHandler(
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}") final String prefix,
            final ObjectMapper objectMapper) {
        this.prefix = prefix;
        this.objectMapper = objectMapper;
    }

    public String topic(final MqttBridge mqtt, final LightDevice device, final String... suffix) {
        return Stream.concat(
                        Stream.of(this.prefix, COMPONENT_PREFIX, mqtt.getClientId(), device.id),
                        Stream.of(suffix))
                .collect(Collectors.joining("/"));
    }

    private <T> MqttBridgeMessage<LightDevice> build(final MqttBridge mqtt, final LightDevice device, final String topic, final T payload) throws JsonProcessingException {
        return new MqttBridgeMessage<>(
                device,
                this,
                this.topic(mqtt, device, topic),
                new MqttMessage(this.objectMapper.writeValueAsBytes(payload)));
    }

    private void subscribe(final MqttBridge mqtt, final DirigeraApi api, final LightDevice device) {
        try {
            mqtt.subscribe(this.topic(mqtt, device, "set"), (topic, message) -> {
                final LightStatus state;

                try {
                    log.debug("Received light command: {}", new String(message.getPayload()));
                    state = LightEventHandler.this.objectMapper.readValue(message.getPayload(), LightStatus.class);

                    Optional.ofNullable(state.state)
                            .map(s -> switch (s) {
                                case ON -> api.device.light.turnOn(device);
                                case OFF -> api.device.light.turnOff(device);
                            })
                            .ifPresent(Mono::block);

                    Optional.ofNullable(state.color_temp)
                            .map(t -> 1_000_000 / t)
                            .map(t -> max(t, device.attributes.state.color.temperatureMax))
                            .map(t -> min(t, device.attributes.state.color.temperatureMin))
                            .map(t -> api.device.light.setTemperature(device, t))
                            .ifPresent(Mono::block);

                    Optional.ofNullable(state.brightness)
                            .map(l -> api.device.light.setLevel(device, l))
                            .ifPresent(Mono::block);

                    Optional.ofNullable(state.color)
                            .map(c -> api.device.light.setColor(device, c.h, c.s / 100.0f))
                            .ifPresent(Mono::block);
                } catch (final Throwable e) {
                    log.error(e.getMessage());
                }
            });
        } catch (final MqttException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public Stream<MqttBridgeMessage<LightDevice>> addDevice(final MqttBridge mqtt, final DirigeraApi api, final LightDevice device) {
        this.subscribe(mqtt, api, device);
        try {
            return Stream.of(this.build(mqtt, device, "config",
                    new LightConfig(
                            device.id,
                            device.id,
                            Optional.ofNullable(device.attributes.state.customName)
                                    .filter(n -> !n.isBlank())
                                    .orElse(device.attributes.model),
                            new Device(
                                    List.of(device.attributes.serialNumber),
                                    device.attributes.manufacturer,
                                    device.attributes.model,
                                    device.attributes.hardwareVersion,
                                    device.attributes.firmwareVersion,
                                    Optional.ofNullable(device.attributes.state.customName)
                                            .filter(n -> !n.isBlank())
                                            .orElse(device.attributes.model),
                                    mqtt.getClientId()),
                            device.capabilities.canReceive.contains("lightLevel"),
                            100,
                            !LightColorMode.colorModes(device).isEmpty(),
                            LightColorMode.colorModes(device),
                            this.topic(mqtt, device, "set"),
                            this.topic(mqtt, device, "state"),
                            "json",
                            Optional.ofNullable(device)
                                    .map(d -> d.attributes)
                                    .map(d -> d.state)
                                    .map(d -> d.color)
                                    .map(d -> d.temperatureMax)
                                    .map(d -> 1_000_000 / d)
                                    .orElse(0),
                            Optional.ofNullable(device)
                                    .map(d -> d.attributes)
                                    .map(d -> d.state)
                                    .map(d -> d.color)
                                    .map(d -> d.temperatureMin)
                                    .map(d -> 1_000_000 / d)
                                    .orElse(0),
                            new DeviceAvailability(
                                    this.topic(mqtt, device, "availability"),
                                    this.objectMapper.writeValueAsString(ONLINE),
                                    this.objectMapper.writeValueAsString(OFFLINE)
                            ))));
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
            return Stream.empty();
        }
    }

    @Override
    public Stream<MqttBridgeMessage<LightDevice>> updateDevice(final MqttBridge mqtt, final DirigeraApi api, final LightDevice device) {
        try {
            return Stream.of(
                    this.build(mqtt, device, "state",
                    new LightStatus(
                            DeviceState.state(device),
                            Optional.of(device)
                                    .map(d -> d.attributes)
                                    .map(d -> d.state)
                                    .map(d -> d.lightLevel)
                                    .orElse(0),
                            LightColorMode.colorMode(device),
                            Optional.of(device)
                                    .map(d -> d.attributes)
                                    .map(d -> d.state)
                                    .map(d -> d.color)
                                    .map(d -> d.temperature)
                                    .map(d -> 1_000_000 / d)
                                    .orElse(0),
                            new LightColor(
                                    Optional.of(device)
                                            .map(d -> d.attributes)
                                            .map(d -> d.state)
                                            .map(d -> d.color)
                                            .map(d -> d.hue)
                                            .orElse(0.0f),
                                    Optional.of(device)
                                            .map(d -> d.attributes)
                                            .map(d -> d.state)
                                            .map(d -> d.color)
                                            .map(d -> d.saturation)
                                            .orElse(0.0f) * 100.0f,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null))),
                    this.build(mqtt, device, "availability", DeviceAvailabilityState.get(device)));

        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return Stream.empty();
        }
    }

    @Override
    public Stream<MqttBridgeMessage<LightDevice>> removeDevice(final MqttBridge mqtt, final DirigeraApi api, final LightDevice device) {
        try {
            return Stream.of(this.build(mqtt, device, "remove", null));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return Stream.empty();
        }
    }
}
