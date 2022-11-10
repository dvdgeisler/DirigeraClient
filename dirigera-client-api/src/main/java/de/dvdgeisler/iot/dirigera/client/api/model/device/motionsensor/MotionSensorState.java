package de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceState;

public class MotionSensorState extends DeviceState<MotionSensorStateAttributes> {

    public MotionSensorState(final Integer transitionTime, final Integer period, final MotionSensorStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public MotionSensorState() {
    }
}
