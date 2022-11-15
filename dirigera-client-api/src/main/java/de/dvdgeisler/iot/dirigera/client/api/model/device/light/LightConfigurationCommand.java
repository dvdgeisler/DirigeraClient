package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceConfigurationCommand;

public class LightConfigurationCommand extends DeviceConfigurationCommand<LightConfigurationAttributes> {
    public LightConfigurationCommand(final LightConfigurationAttributes attributes) {
        super(attributes);
    }

    public LightConfigurationCommand() {
    }
}
