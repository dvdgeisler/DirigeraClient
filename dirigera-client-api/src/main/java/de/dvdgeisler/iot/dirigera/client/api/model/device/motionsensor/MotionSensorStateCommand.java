package de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateCommand;

public class MotionSensorStateCommand extends DeviceStateCommand<MotionSensorStateAttributes> {

    public MotionSensorStateCommand(final Integer transitionTime, final Integer period, final MotionSensorStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public MotionSensorStateCommand() {
    }
}
