package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;



public class LightDevice extends Device<LightAttributes, LightConfigurationAttributes> {

    public LightDevice() {
    }

    public LightDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final LightAttributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks, final LightConfigurationAttributes lightConfigurationAttributes) {
        super(id, DeviceCategory.LIGHT, DeviceType.LIGHT, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks, lightConfigurationAttributes);
    }
}
