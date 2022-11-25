package de.dvdgeisler.iot.dirigera.client.api.model.device.lightcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType.LIGHT_CONTROLLER;

public class LightControllerDevice extends Device<LightControllerAttributes, LightControllerConfigurationAttributes> {

    public LightControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final LightControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final LightControllerConfigurationAttributes lightControllerConfigurationAttributes) {
        super(id, CONTROLLER, LIGHT_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, lightControllerConfigurationAttributes);
    }

    public LightControllerDevice() {
    }
}
