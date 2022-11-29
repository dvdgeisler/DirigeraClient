package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class LightDeviceApi extends DefaultDeviceApi<
        LightStateAttributes,
        LightAttributes,
        LightConfigurationAttributes,
        LightDevice> {
    public LightDeviceApi(final ClientApi clientApi, final WebSocketApi webSocketApi) {
        super(clientApi, webSocketApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof LightDevice;
    }

    public Mono<LightDevice> setLevel(final LightDevice device, final Integer lightLevel) {
        final LightStateAttributes attributes;

        if (lightLevel < 0 || lightLevel > 100)
            return Mono.error(new IllegalArgumentException("Light level must be between 0 and 100"));

        attributes = new LightStateAttributes();
        attributes.lightLevel = lightLevel;

        return this.assertCapability(device, "lightLevel")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<LightDevice> setLevelTransition(final LightDevice device, final Duration transtion, final Integer lightLevel) {
        final LightStateAttributes attributes;

        if (lightLevel < 0 || lightLevel > 100)
            throw new IllegalArgumentException("Light level must be between 0 and 100");

        attributes = new LightStateAttributes();
        attributes.lightLevel = lightLevel;

        return this.assertCapability(device, "lightLevel")
                .flatMap(d -> this.setStateAttributeTransition(d, transtion, attributes));
    }

    public Mono<LightDevice> setLevelAfterPeriod(final LightDevice device, final Duration period, final Integer lightLevel) {
        final LightStateAttributes attributes;

        if (lightLevel < 0 || lightLevel > 100)
            throw new IllegalArgumentException("Light level must be between 0 and 100");

        attributes = new LightStateAttributes();
        attributes.lightLevel = lightLevel;

        return this.assertCapability(device, "lightLevel")
                .flatMap(d -> this.setStateAttributeAfterPeriod(d, period, attributes));
    }

    public Mono<LightDevice> setColor(final LightDevice device, final Float hue, final Float saturation) {
        final LightStateAttributes attributes;

        if (hue < 0 || hue > 360)
            return Mono.error(new IllegalArgumentException("Light Hue must be between 0 and 360"));

        if (saturation < 0 || saturation > 1)
            return Mono.error(new IllegalArgumentException("Light Saturation must be between 0 and 360"));

        attributes = new LightStateAttributes();
        attributes.color = new LightColorAttributes(
                hue,
                saturation,
                null,
                null,
                null,
                null);

        return this.assertCapability(device, List.of("colorHue", "colorSaturation"))
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<LightDevice> setColorTransition(final LightDevice device, final Duration transtion, final Float hue, final Float saturation) {
        final LightStateAttributes attributes;

        if (hue < 0 || hue > 360)
            return Mono.error(new IllegalArgumentException("Light Hue must be between 0 and 360"));

        if (saturation < 0 || saturation > 1)
            return Mono.error(new IllegalArgumentException("Light Saturation must be between 0 and 360"));

        attributes = new LightStateAttributes();
        attributes.color = new LightColorAttributes(
                hue,
                saturation,
                null,
                null,
                null,
                null);

        return this.assertCapability(device, List.of("colorHue", "colorSaturation"))
                .flatMap(d -> this.setStateAttributeTransition(d, transtion, attributes));
    }

    public Mono<LightDevice> setColorAfterPeriod(final LightDevice device, final Duration period, final Float hue, final Float saturation) {
        final LightStateAttributes attributes;

        if (hue < 0 || hue > 360)
            return Mono.error(new IllegalArgumentException("Light Hue must be between 0 and 360"));

        if (saturation < 0 || saturation > 1)
            return Mono.error(new IllegalArgumentException("Light Saturation must be between 0 and 360"));

        attributes = new LightStateAttributes();
        attributes.color = new LightColorAttributes(
                hue,
                saturation,
                null,
                null,
                null,
                null);

        return this.assertCapability(device, List.of("colorHue", "colorSaturation"))
                .flatMap(d -> this.setStateAttributeAfterPeriod(d, period, attributes));
    }

    public Mono<LightDevice> setTemperature(final LightDevice device, final Integer temperature) {
        final LightStateAttributes attributes;

        if (temperature > device.attributes.state.color.temperatureMin || temperature < device.attributes.state.color.temperatureMax)
            return Mono.error(new IllegalArgumentException(String.format("Light Temperature must be between %d and %d", device.attributes.state.color.temperatureMax, device.attributes.state.color.temperatureMin)));


        attributes = new LightStateAttributes();
        attributes.color = new LightColorAttributes(
                null,
                null,
                temperature,
                null,
                null,
                null);

        return this.assertCapability(device, "colorTemperature")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<LightDevice> setTemperatureTransition(final LightDevice device, final Duration transtion, final Integer temperature) {
        final LightStateAttributes attributes;

        if (temperature > device.attributes.state.color.temperatureMin || temperature < device.attributes.state.color.temperatureMax)
            return Mono.error(new IllegalArgumentException(String.format("Light Temperature must be between %d and %d", device.attributes.state.color.temperatureMax, device.attributes.state.color.temperatureMin)));


        attributes = new LightStateAttributes();
        attributes.color = new LightColorAttributes(
                null,
                null,
                temperature,
                null,
                null,
                null);

        return this.assertCapability(device, "colorTemperature")
                .flatMap(d -> this.setStateAttributeTransition(d, transtion, attributes));
    }

    public Mono<LightDevice> setTemperatureAfterPeriod(final LightDevice device, final Duration period, final Integer temperature) {
        final LightStateAttributes attributes;

        if (temperature > device.attributes.state.color.temperatureMin || temperature < device.attributes.state.color.temperatureMax)
            return Mono.error(new IllegalArgumentException(String.format("Light Temperature must be between %d and %d", device.attributes.state.color.temperatureMax, device.attributes.state.color.temperatureMin)));


        attributes = new LightStateAttributes();
        attributes.color = new LightColorAttributes(
                null,
                null,
                temperature,
                null,
                null,
                null);

        return this.assertCapability(device, "colorTemperature")
                .flatMap(d -> this.setStateAttributeAfterPeriod(d, period, attributes));
    }

    public Mono<LightDevice> turnOn(final LightDevice device) {
        final LightStateAttributes attributes;

        attributes = new LightStateAttributes();
        attributes.isOn = true;

        return this.assertCapability(device, "isOn").flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<LightDevice> turnOff(final LightDevice device) {
        final LightStateAttributes attributes;

        attributes = new LightStateAttributes();
        attributes.isOn = false;

        return this.assertCapability(device, "isOn").flatMap(d -> this.setStateAttribute(d, attributes));
    }
}
