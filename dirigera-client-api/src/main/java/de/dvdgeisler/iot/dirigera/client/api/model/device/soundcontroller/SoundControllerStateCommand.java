package de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateCommand;

public class SoundControllerStateCommand extends DeviceStateCommand<SoundControllerStateAttributes> {

    public SoundControllerStateCommand(final Integer transitionTime, final Integer period, final SoundControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public SoundControllerStateCommand() {
    }
}
