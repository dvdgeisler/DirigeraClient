package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;

public class LightControllerDevice extends Device<LightControllerAttributes, LightControllerConfigurationAttributes> {

    public LightControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final LightControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final LightControllerConfigurationAttributes lightControllerConfigurationAttributes) {
        super(id, DeviceCategory.CONTROLLER, DeviceType.LIGHT_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, lightControllerConfigurationAttributes);
    }

    public LightControllerDevice() {
    }
}
