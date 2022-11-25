package de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceConfigurationCommand;

public class MotionSensorConfigurationCommand extends DeviceConfigurationCommand<MotionSensorConfigurationAttributes> {
    public MotionSensorConfigurationCommand(final MotionSensorConfigurationAttributes attributes) {
        super(attributes);
    }

    public MotionSensorConfigurationCommand() {
    }
}
