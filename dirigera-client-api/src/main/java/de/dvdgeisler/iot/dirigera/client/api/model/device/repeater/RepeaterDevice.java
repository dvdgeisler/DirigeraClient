package de.dvdgeisler.iot.dirigera.client.api.model.device.repeater;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;

import java.time.LocalDateTime;
import java.util.List;


public class RepeaterDevice extends Device<RepeaterAttributes, RepeaterConfigurationAttributes> {

    public RepeaterDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final RepeaterAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final RepeaterConfigurationAttributes repeaterConfigurationAttributes) {
        super(id, DeviceCategory.REPEATER, DeviceType.REPEATER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, repeaterConfigurationAttributes);
    }

    public RepeaterDevice() {
    }
}
