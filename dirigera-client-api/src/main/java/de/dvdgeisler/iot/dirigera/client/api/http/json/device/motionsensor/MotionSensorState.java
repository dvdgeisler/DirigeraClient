package de.dvdgeisler.iot.dirigera.client.api.http.json.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceState;

public class MotionSensorState extends DeviceState<MotionSensorStateAttributes> {

    public MotionSensorState(final Integer transitionTime, final Integer period, final MotionSensorStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public MotionSensorState() {
    }
}
