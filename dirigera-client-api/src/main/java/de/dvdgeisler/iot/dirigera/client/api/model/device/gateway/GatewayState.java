package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceState;

public class GatewayState extends DeviceState<GatewayStateAttributes> {
    public GatewayState(final Integer transitionTime, final Integer period, final GatewayStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public GatewayState() {
    }
}
