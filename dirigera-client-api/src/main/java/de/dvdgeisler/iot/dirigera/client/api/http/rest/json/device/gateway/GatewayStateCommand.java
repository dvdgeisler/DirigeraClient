package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateCommand;

public class GatewayStateCommand extends DeviceStateCommand<GatewayStateAttributes> {
    public GatewayStateCommand(final Integer transitionTime, final Integer period, final GatewayStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public GatewayStateCommand() {
    }
}
