package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier.*;
import reactor.core.publisher.Mono;

public class AirPurifierDeviceApi extends DefaultDeviceApi<
        AirPurifierStateAttributes,
        AirPurifierAttributes,
        AirPurifierConfigurationAttributes,
        AirPurifierDevice> {
    public AirPurifierDeviceApi(final ClientApi clientApi) {
        super(clientApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof AirPurifierDevice;
    }

    public Mono<AirPurifierDevice> setFanMode(final AirPurifierDevice device, final AirPurifierFanMode fanMode) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.fanMode = fanMode;

        return this.assertCapability(device, "fanMode")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<AirPurifierDevice> setFanModeSequence(final AirPurifierDevice device, final AirPurifierFanModeSequence fanModeSequence) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.fanModeSequence = fanModeSequence;

        return this.assertCapability(device, "fanModeSequence")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<AirPurifierDevice> setMotorState(final AirPurifierDevice device, final Integer motorState) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.motorState = motorState;

        return this.assertCapability(device, "motorState")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<AirPurifierDevice> enableChildLock(final AirPurifierDevice device) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.childLock = Boolean.TRUE;

        return this.assertCapability(device, "childLock")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<AirPurifierDevice> disableChildLock(final AirPurifierDevice device) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.childLock = Boolean.FALSE;

        return this.assertCapability(device, "childLock")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<AirPurifierDevice> enableStatusLight(final AirPurifierDevice device) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.statusLight = Boolean.TRUE;

        return this.assertCapability(device, "statusLight")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<AirPurifierDevice> disableStatusLight(final AirPurifierDevice device) {
        final AirPurifierStateAttributes attributes;

        attributes = new AirPurifierStateAttributes();
        attributes.statusLight = Boolean.FALSE;

        return this.assertCapability(device, "statusLight")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }
}
