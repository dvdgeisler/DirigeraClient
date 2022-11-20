package de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType.BLINDS_CONTROLLER;

public class BlindsControllerDevice extends Device<BlindsControllerAttributes, BlindsControllerConfigurationAttributes> {

    public BlindsControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final BlindsControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final BlindsControllerConfigurationAttributes configurationAttributes) {
        super(id, CONTROLLER, BLINDS_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public BlindsControllerDevice() {
    }
}
