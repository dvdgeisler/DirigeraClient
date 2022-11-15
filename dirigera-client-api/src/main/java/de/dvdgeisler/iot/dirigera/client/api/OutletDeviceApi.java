package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletDevice;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletStateAttributes;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OutletDeviceApi extends DefaultDeviceApi<
        OutletStateAttributes,
        OutletAttributes,
        OutletConfigurationAttributes,
        OutletDevice> {
    public OutletDeviceApi(final ClientApi clientApi) {
        super(clientApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof OutletDevice;
    }

    public Mono<OutletDevice> setLevel(final OutletDevice device, final Integer lightLevel) {
        final OutletStateAttributes attributes;

        if(lightLevel < 0 || lightLevel > 100)
            return Mono.error(new IllegalArgumentException("Light level must be between 0 and 100"));

        attributes = new OutletStateAttributes();
        attributes.lightLevel = lightLevel;

        return this.assertCapability(device, "lightLevel")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<OutletDevice> setLevelTransition(final OutletDevice device, final Duration transtion, final Integer lightLevel) {
        final OutletStateAttributes attributes;

        if(lightLevel < 0 || lightLevel > 100)
            throw new IllegalArgumentException("Light level must be between 0 and 100");

        attributes = new OutletStateAttributes();
        attributes.lightLevel = lightLevel;

        return this.assertCapability(device, "lightLevel")
                .flatMap(d -> this.setStateAttributeTransition(d, transtion, attributes));
    }

    public Mono<OutletDevice> setLevelAfterPeriod(final OutletDevice device, final Duration period, final Integer lightLevel) {
        final OutletStateAttributes attributes;

        if(lightLevel < 0 || lightLevel > 100)
            throw new IllegalArgumentException("Light level must be between 0 and 100");

        attributes = new OutletStateAttributes();
        attributes.lightLevel = lightLevel;

        return this.assertCapability(device, "lightLevel")
                .flatMap(d -> this.setStateAttributeAfterPeriod(d, period, attributes));
    }

    public Mono<OutletDevice> turnOn(final OutletDevice device) {
        final OutletStateAttributes attributes;

        attributes = new OutletStateAttributes();
        attributes.isOn = true;

        return this.assertCapability(device, "isOn")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<OutletDevice> turnOnTransition(final OutletDevice device, final Duration transition) {
        final OutletStateAttributes attributes;

        attributes = new OutletStateAttributes();
        attributes.isOn = true;

        return this.assertCapability(device, "isOn")
                .flatMap(d -> this.setStateAttributeTransition(d, transition, attributes));
    }

    public Mono<OutletDevice> turnOnAfterPeriod(final OutletDevice device, final Duration period) {
        final OutletStateAttributes attributes;

        attributes = new OutletStateAttributes();
        attributes.isOn = true;

        return this.assertCapability(device, "isOn")
                .flatMap(d -> this.setStateAttributeAfterPeriod(d, period, attributes));
    }

    public Mono<OutletDevice> turnOff(final OutletDevice device) {
        final OutletStateAttributes attributes;

        attributes = new OutletStateAttributes();
        attributes.isOn = false;

        return this.assertCapability(device, "isOn")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<OutletDevice> turnOffTransition(final OutletDevice device, final Duration transition) {
        final OutletStateAttributes attributes;

        attributes = new OutletStateAttributes();
        attributes.isOn = false;

        return this.assertCapability(device, "isOn")
                .flatMap(d -> this.setStateAttributeTransition(d, transition, attributes));
    }

    public Mono<OutletDevice> turnOffAfterPeriod(final OutletDevice device, final Duration period) {
        final OutletStateAttributes attributes;

        attributes = new OutletStateAttributes();
        attributes.isOn = false;

        return this.assertCapability(device, "isOn")
                .flatMap(d -> this.setStateAttributeAfterPeriod(d, period, attributes));
    }
}
