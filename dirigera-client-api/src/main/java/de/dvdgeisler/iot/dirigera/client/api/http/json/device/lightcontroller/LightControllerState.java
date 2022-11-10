package de.dvdgeisler.iot.dirigera.client.api.http.json.device.lightcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceState;

public class LightControllerState extends DeviceState<LightControllerStateAttributes> {

    public LightControllerState(final Integer transitionTime, final Integer period, final LightControllerStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public LightControllerState() {
    }
}
