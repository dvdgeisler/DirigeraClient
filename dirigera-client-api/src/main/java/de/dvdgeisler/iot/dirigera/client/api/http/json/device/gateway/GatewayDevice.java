package de.dvdgeisler.iot.dirigera.client.api.http.json.device.gateway;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;

public class GatewayDevice extends Device<GatewayAttributes> {

    public Boolean isHidden;

    public GatewayDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final GatewayAttributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks, final Boolean isHidden) {
        super(id, DeviceCategory.GATEWAY, DeviceType.GATEWAY, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks);
        this.isHidden = isHidden;
    }

    public GatewayDevice() {
    }
}
