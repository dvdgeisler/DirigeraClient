package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridge;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridgeMessage;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Stream;

import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceUtils.*;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightUtils.getState;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightUtils.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
public class LightEventHandler extends HassEventHandler<LightDevice, LightStatus> {
    private final static Logger log = LoggerFactory.getLogger(LightEventHandler.class);
    private final static String HASS_COMPONENT = "light";
    private final static String TOPIC_CONFIG = "config";
    private final static String TOPIC_STATE = "state";
    private final static String TOPIC_SET = "set";
    private final static String TOPIC_AVAILABILITY = "availability";
    private final static String TOPIC_REMOVE = "remove";
    private final static int LIGHT_LEVEL_MIN = 0;
    private final static int LIGHT_LEVEL_MAX = 100;

    public LightEventHandler(
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}") final String prefix,
            final ObjectMapper objectMapper) {
        super(prefix, HASS_COMPONENT, objectMapper, LightStatus.class);
    }

    @Override
    public void setDevice(final MqttBridge mqtt, final DirigeraApi api, final LightDevice device, final LightStatus status) {
        Optional.ofNullable(status.state)
                .filter(t -> canReceive(device, "isOn"))
                .map(s -> switch (s) {
                    case ON -> api.device.light.turnOn(device);
                    case OFF -> api.device.light.turnOff(device);
                })
                .ifPresent(Mono::block);

        Optional.ofNullable(status.color_temp)
                .filter(t -> canReceive(device, "colorTemperature"))
                .map(t -> max(t, getMaxTemperatureMireds(device))) // todo: getMaxTemperatureMireds may return null
                .map(t -> min(t, getMinTemperatureMireds(device))) // todo: getMinTemperatureMireds may return null
                .map(LightUtils::miredsToKelvin)
                .map(t -> api.device.light.setTemperature(device, t))
                .ifPresent(Mono::block);

        Optional.ofNullable(status.brightness)
                .filter(t -> canReceive(device, "lightLevel"))
                .map(l -> max(l, LIGHT_LEVEL_MIN))
                .map(l -> min(l, LIGHT_LEVEL_MAX))
                .map(l -> api.device.light.setLevel(device, l))
                .ifPresent(Mono::block);

        Optional.ofNullable(status.color)
                .filter(t -> canReceive(device, "colorHue", "colorSaturation"))
                .filter(c -> Objects.nonNull(c.h))
                .filter(c -> Objects.nonNull(c.s))
                .map(c -> api.device.light.setColor(device, c.h, c.s / 100.0f))
                .ifPresent(Mono::block);
    }


    @Override
    public Stream<MqttBridgeMessage<LightDevice>> addDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final LightDevice device) {
        final List<LightColorMode> colorModes;

        colorModes = colorModes(device);
        this.subscribe(mqtt, api, device);
        return Stream.of(this.build(mqtt, device, TOPIC_CONFIG,
                new LightConfig(
                        device.id,
                        device.id,
                        getDefaultName(device),
                        getDeviceConfig(mqtt, device),
                        canReceive(device, "lightLevel"),
                        LIGHT_LEVEL_MAX,
                        !colorModes.isEmpty(),
                        colorModes,
                        this.topic(mqtt, device, TOPIC_SET),
                        this.topic(mqtt, device, TOPIC_STATE),
                        "json",
                        getMaxTemperatureMireds(device),
                        getMinTemperatureMireds(device),
                        this.getDeviceAvailabilityConfig(mqtt, device, TOPIC_AVAILABILITY))));
    }

    @Override
    public Stream<MqttBridgeMessage<LightDevice>> updateDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final LightDevice device) {
        return Stream.of(
                this.build(mqtt, device, TOPIC_STATE,
                        new LightStatus(
                                getState(device),
                                getLightLevel(device),
                                colorMode(device),
                                getTemperatureMireds(device),
                                new LightColor(
                                        getHue(device), Optional.ofNullable(getSaturation(device)).map(s -> s * 100.0f).orElse(null),
                                        null, null, null,
                                        null, null,
                                        null, null))),
                this.build(mqtt, device, TOPIC_AVAILABILITY, getAvailability(device)));
    }

    @Override
    public Stream<MqttBridgeMessage<LightDevice>> removeDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final LightDevice device) {
        return Stream.of(this.build(mqtt, device, TOPIC_REMOVE, null));
    }
}
