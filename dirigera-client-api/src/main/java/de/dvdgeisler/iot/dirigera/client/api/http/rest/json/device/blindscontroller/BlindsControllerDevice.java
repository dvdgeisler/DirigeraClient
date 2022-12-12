package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.blindscontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;

public class BlindsControllerDevice extends Device<BlindsControllerAttributes, BlindsControllerConfigurationAttributes> {

    public BlindsControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final BlindsControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final BlindsControllerConfigurationAttributes configurationAttributes) {
        super(id, DeviceCategory.CONTROLLER, DeviceType.BLINDS_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, configurationAttributes);
    }

    public BlindsControllerDevice() {
    }
}
