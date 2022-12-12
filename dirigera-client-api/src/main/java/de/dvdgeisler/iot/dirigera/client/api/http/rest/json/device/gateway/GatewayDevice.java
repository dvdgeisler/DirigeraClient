package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;

public class GatewayDevice extends Device<GatewayAttributes, GatewayConfigurationAttributes> {

    public GatewayDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final GatewayAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final GatewayConfigurationAttributes configurationAttributes) {
        super(id, DeviceCategory.GATEWAY, DeviceType.GATEWAY, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public GatewayDevice() {
    }
}
