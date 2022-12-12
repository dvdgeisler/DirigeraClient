package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.airpurifier;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;


public class AirPurifierDevice extends Device<AirPurifierAttributes, AirPurifierConfigurationAttributes> {

    public AirPurifierDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final AirPurifierAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final AirPurifierConfigurationAttributes configurationAttributes) {
        super(id, DeviceCategory.AIR_PURIFIER, DeviceType.AIR_PURIFIER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public AirPurifierDevice() {
    }
}
