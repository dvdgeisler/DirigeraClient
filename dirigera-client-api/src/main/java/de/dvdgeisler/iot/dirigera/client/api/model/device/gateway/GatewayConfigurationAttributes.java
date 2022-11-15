package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.util.List;

public class GatewayConfigurationAttributes extends DeviceConfigurationAttributes {
    public GatewayConfigurationAttributes() {
    }

    public GatewayConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet) {
        super(customIcon, deviceSet);
    }
}
