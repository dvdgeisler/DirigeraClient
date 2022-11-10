package de.dvdgeisler.iot.dirigera.client.api.http.json.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceState;

public class SoundControllerState extends DeviceState<SoundControllerStateAttributes> {

    public SoundControllerState(final Integer transitionTime, final Integer period, final SoundControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public SoundControllerState() {
    }
}
