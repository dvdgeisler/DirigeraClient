package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.LightDeviceApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.light.LightDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightColor;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightColorMode;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightConfig;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light.LightStatus;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
public class HassLightDeviceEventHandler extends HassDeviceEventHandler<LightDevice> {
    private final static String HASS_COMPONENT = "light";
    private final static int LIGHT_LEVEL_MIN = 0;
    private final static int LIGHT_LEVEL_MAX = 100;

    private final LightDeviceApi api;

    public HassLightDeviceEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}")
            final String topicPrefix,
            final ObjectMapper objectMapper) {
        super(mqtt, api, LightDevice.class, topicPrefix, objectMapper);

        this.api = api.device.light;
        this.api.all()
                .block()
                .forEach(this::onDeviceCreated);
    }

    @Override
    protected void onDeviceCreated(final LightDevice device) {
        final List<LightColorMode> colorModes;
        final LightConfig config;

        colorModes = colorModes(device);

        config = new LightConfig();
        config.unique_id = config.object_id = device.id;
        config.name = getDefaultName(device);
        config.device = this.getDeviceConfig(device);
        config.brightness = canReceive(device, "lightLevel");
        if (config.brightness)
            config.brightness_scale = LIGHT_LEVEL_MAX;
        config.color_mode = !colorModes.isEmpty();
        if (config.color_mode)
            config.supported_color_modes = colorModes;
        config.command_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_SET);
        config.state_topic = this.getTopic(device, HASS_COMPONENT, TOPIC_STATE);
        config.schema = "json";
        getMaxTemperatureMireds(device).ifPresent(max_mireds -> config.max_mireds = max_mireds);
        getMinTemperatureMireds(device).ifPresent(min_mireds -> config.min_mireds = min_mireds);

        config.availability = new DeviceAvailability();
        config.availability.topic = this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY);
        config.availability.payload_available = this.toJSON(DeviceAvailabilityState.ONLINE);
        config.availability.payload_not_available = this.toJSON(DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_CONFIG), config);

        this.subscribe(this.getTopic(device, HASS_COMPONENT, TOPIC_SET),
                LightStatus.class,
                status -> this.setDeviceStatus(device, status));

        this.onDeviceStateChanged(device);
    }

    protected void setDeviceStatus(final LightDevice device, final LightStatus status) {
        Optional.ofNullable(status.state)
                .filter(t -> canReceive(device, "isOn"))
                .map(s -> switch (s) {
                    case ON -> this.api.turnOn(device);
                    case OFF -> this.api.turnOff(device);
                })
                .ifPresent(Mono::block);

        Optional.ofNullable(status.color_temp)
                .filter(t -> canReceive(device, "colorTemperature"))
                .flatMap(t -> getMaxTemperatureMireds(device).map(maxT -> min(t, maxT)))
                .flatMap(t -> getMinTemperatureMireds(device).map(minT -> max(t, minT)))
                .map(HassLightDeviceEventHandler::miredsToKelvin)
                .map(t -> this.api.setTemperature(device, t))
                .ifPresent(Mono::block);

        Optional.ofNullable(status.brightness)
                .filter(t -> canReceive(device, "lightLevel"))
                .map(l -> max(l, LIGHT_LEVEL_MIN))
                .map(l -> min(l, LIGHT_LEVEL_MAX))
                .map(l -> this.api.setLevel(device, l))
                .ifPresent(Mono::block);

        Optional.ofNullable(status.color)
                .filter(t -> canReceive(device, "colorHue", "colorSaturation"))
                .filter(c -> Objects.nonNull(c.h))
                .filter(c -> Objects.nonNull(c.s))
                .map(c -> this.api.setColor(device, c.h, c.s / 100.0f))
                .ifPresent(Mono::block);
    }

    @Override
    protected void onDeviceStateChanged(final LightDevice device) {
        final LightStatus status;

        status = new LightStatus();
        getState(device).ifPresent(state -> status.state = state);
        getLightLevel(device).ifPresent(brightness -> status.brightness = brightness);
        getColorMode(device).ifPresent(color_mode -> status.color_mode = color_mode);
        getTemperatureMireds(device).ifPresent(color_temp -> status.color_temp = color_temp);

        status.color = new LightColor();
        getHue(device).ifPresent(h -> status.color.h = h);
        getSaturation(device).ifPresent(s -> status.color.s = s * 100.0f);

        this.publish(
                this.getTopic(device, HASS_COMPONENT, TOPIC_STATE),
                status);

        getAvailability(device).ifPresent(s ->
                this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), s));
    }

    @Override
    protected void onDeviceRemoved(final LightDevice device) {
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_AVAILABILITY), DeviceAvailabilityState.OFFLINE);
        this.publish(this.getTopic(device, HASS_COMPONENT, TOPIC_REMOVE), null);
    }

    public static Integer kelvinToMireds(final Integer kelvin) {
        return Optional.ofNullable(kelvin)
                .filter(k -> k > 0)
                .map(k -> 1_000_000 / k)
                .orElse(null);
    }

    public static Integer miredsToKelvin(final Integer kelvin) {
        return Optional.ofNullable(kelvin)
                .filter(k -> k > 0)
                .map(k -> 1_000_000 / k)
                .orElse(null);
    }

    public static Optional<Integer> getMaxTemperatureMireds(final LightDevice device) {
        return getMinTemperatureKelvin(device)
                .map(HassLightDeviceEventHandler::kelvinToMireds);
    }

    public static Optional<Integer> getMaxTemperatureKelvin(final LightDevice device) {
        return Optional.ofNullable(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.temperatureMin);
    }

    public static Optional<Integer> getMinTemperatureMireds(final LightDevice device) {
        return getMaxTemperatureKelvin(device)
                .map(HassLightDeviceEventHandler::miredsToKelvin);
    }

    public static Optional<Integer> getMinTemperatureKelvin(final LightDevice device) {
        return Optional.ofNullable(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.temperatureMax);
    }

    public static Optional<Integer> getTemperatureMireds(final LightDevice device) {
        return getTemperatureKelvin(device)
                .map(HassLightDeviceEventHandler::kelvinToMireds);
    }

    public static Optional<Integer> getTemperatureKelvin(final LightDevice device) {
        return Optional.ofNullable(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.temperature);
    }

    public static Optional<Integer> getLightLevel(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.lightLevel);
    }

    public static Optional<Float> getHue(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.hue);
    }

    public static Optional<Float> getSaturation(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.saturation);
    }

    public static List<LightColorMode> colorModes(final LightDevice device) {
        final List<LightColorMode> colorModes;

        colorModes = new ArrayList<>();

        if (Optional.of(device)
                .map(d -> d.capabilities)
                .map(d -> d.canReceive)
                .filter(d -> d.contains("colorHue"))
                .filter(d -> d.contains("colorSaturation"))
                .isPresent())
            colorModes.add(LightColorMode.HS);

        if (Optional.of(device)
                .map(d -> d.capabilities)
                .map(d -> d.canReceive)
                .filter(d -> d.contains("colorTemperature"))
                .isPresent())
            colorModes.add(LightColorMode.COLOR_TEMP);

        if (!colorModes.isEmpty())
            return colorModes;

        if (Optional.of(device)
                .map(d -> d.capabilities)
                .map(d -> d.canReceive)
                .filter(d -> d.contains("lightLevel"))
                .isPresent())
            colorModes.add(LightColorMode.BRIGHTNESS);

        if (!colorModes.isEmpty())
            return colorModes;

        if (Optional.of(device)
                .map(d -> d.capabilities)
                .map(d -> d.canReceive)
                .filter(d -> d.contains("isOn"))
                .isPresent())
            colorModes.add(LightColorMode.ONOFF);

        return colorModes;
    }

    public static Optional<LightColorMode> getColorMode(final LightDevice device) {
        Optional<LightColorMode> r;

        r = Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.color)
                .map(d -> d.mode)
                .map(mode ->
                        switch (mode) {
                            case COLOR -> LightColorMode.HS;
                            case TEMPERATURE -> LightColorMode.COLOR_TEMP;
                        }
                );

        if (r.isPresent())
            return r;

        r = Optional.of(device)
                .map(d -> d.capabilities)
                .map(d -> d.canReceive)
                .map(d -> {
                    if (d.contains("lightLevel"))
                        return LightColorMode.BRIGHTNESS;
                    if (d.contains("isOn"))
                        return LightColorMode.ONOFF;
                    else return null;
                });
        return r;

    }

    public static Optional<DeviceState> getState(final LightDevice device) {
        return Optional.of(device)
                .map(d -> d.attributes)
                .map(d -> d.state)
                .map(d -> d.isOn)
                .map(d -> d ? DeviceState.ON : DeviceState.OFF);
    }
}
