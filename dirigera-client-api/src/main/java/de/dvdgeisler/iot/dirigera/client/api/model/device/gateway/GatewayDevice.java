package de.dvdgeisler.iot.dirigera.client.api.model.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;

public class GatewayDevice extends Device<GatewayAttributes, GatewayConfigurationAttributes> {

    public GatewayDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final GatewayAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final GatewayConfigurationAttributes configurationAttributes) {
        super(id, DeviceCategory.GATEWAY, DeviceType.GATEWAY, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public GatewayDevice() {
    }
}
