package de.dvdgeisler.iot.dirigera.client.api;

import de.dvdgeisler.iot.dirigera.client.api.http.ClientApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blinds.*;
import reactor.core.publisher.Mono;

public class BlindsDeviceApi extends DefaultDeviceApi<
        BlindsStateAttributes,
        BlindsAttributes,
        BlindsConfigurationAttributes,
        BlindsDevice> {
    public BlindsDeviceApi(final ClientApi clientApi) {
        super(clientApi);
    }

    @Override
    protected boolean isInstance(final Device<?, ?> device) {
        return device instanceof BlindsDevice;
    }

    public Mono<BlindsDevice> setTargetLevel(final BlindsDevice device, final Integer targetLevel) {
        final BlindsStateAttributes attributes;

        if(targetLevel < 0 || targetLevel > 100)
            return Mono.error(new IllegalArgumentException("Target level must be between 0 and 100"));

        attributes = new BlindsStateAttributes();
        attributes.blindsTargetLevel = targetLevel;

        return this.assertCapability(device, "blindsTargetLevel")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<BlindsDevice> setCurrentLevel(final BlindsDevice device, final Integer currentLevel) {
        final BlindsStateAttributes attributes;

        if(currentLevel < 0 || currentLevel > 100)
            return Mono.error(new IllegalArgumentException("Target level must be between 0 and 100"));

        attributes = new BlindsStateAttributes();
        attributes.blindsCurrentLevel = currentLevel;

        return this.assertCapability(device, "blindsCurrentLevel")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }

    public Mono<BlindsDevice> setState(final BlindsDevice device, final BlindsState state) {
        final BlindsStateAttributes attributes;

        attributes = new BlindsStateAttributes();
        attributes.blindsState = state;

        return this.assertCapability(device, "blindsState")
                .flatMap(d -> this.setStateAttribute(d, attributes));
    }
}
