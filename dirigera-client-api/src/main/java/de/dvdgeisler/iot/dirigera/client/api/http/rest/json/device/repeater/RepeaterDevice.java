package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;


public class RepeaterDevice extends Device<RepeaterAttributes, RepeaterConfigurationAttributes> {

    public RepeaterDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final RepeaterAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final RepeaterConfigurationAttributes repeaterConfigurationAttributes) {
        super(id, DeviceCategory.REPEATER, DeviceType.REPEATER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, repeaterConfigurationAttributes);
    }

    public RepeaterDevice() {
    }
}
