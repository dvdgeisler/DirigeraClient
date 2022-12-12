package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceConfigurationAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;

import java.util.List;

public class GatewayConfigurationAttributes extends DeviceConfigurationAttributes {
    public GatewayConfigurationAttributes() {
    }

    public GatewayConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet) {
        super(customIcon, deviceSet);
    }
}
