package de.dvdgeisler.iot.dirigera.client.api.model.device.blinds;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;


public class BlindsDevice extends Device<BlindsAttributes, BlindsConfigurationAttributes> {

    public BlindsDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final BlindsAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final BlindsConfigurationAttributes configurationAttributes) {
        super(id, DeviceCategory.BLINDS, DeviceType.BLINDS, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public BlindsDevice() {
    }
}
