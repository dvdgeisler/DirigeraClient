package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.outlet;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;


public class OutletDevice extends Device<OutletAttributes, OutletConfigurationAttributes> {

    public OutletDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final OutletAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final OutletConfigurationAttributes outletConfigurationAttributes) {
        super(id, DeviceCategory.OUTLET, DeviceType.OUTLET, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, outletConfigurationAttributes);
    }

    public OutletDevice() {
    }
}
