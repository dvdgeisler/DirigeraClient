package de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;


public class AirPurifierDevice extends Device<AirPurifierAttributes, AirPurifierConfigurationAttributes> {

    public AirPurifierDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final AirPurifierAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final AirPurifierConfigurationAttributes configurationAttributes) {
        super(id, DeviceCategory.AIR_PURIFIER, DeviceType.AIR_PURIFIER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public AirPurifierDevice() {
    }
}
