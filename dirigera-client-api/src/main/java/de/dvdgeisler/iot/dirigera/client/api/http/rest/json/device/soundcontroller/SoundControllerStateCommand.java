package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateCommand;

public class SoundControllerStateCommand extends DeviceStateCommand<SoundControllerStateAttributes> {

    public SoundControllerStateCommand(final Integer transitionTime, final Integer period, final SoundControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public SoundControllerStateCommand() {
    }
}
