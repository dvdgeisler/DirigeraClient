package de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateCommand;

public class LightControllerStateCommand extends DeviceStateCommand<LightControllerStateAttributes> {

    public LightControllerStateCommand(final Integer transitionTime, final Integer period, final LightControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public LightControllerStateCommand() {
    }
}
