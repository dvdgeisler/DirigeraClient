package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.shortcutcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType.SHORTCUT_CONTROLLER;

public class ShortcutControllerDevice extends Device<ShortcutControllerAttributes, ShortcutControllerConfigurationAttributes> {


    public ShortcutControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final ShortcutControllerAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final ShortcutControllerConfigurationAttributes shortcutControllerConfigurationAttributes) {
        super(id, CONTROLLER, SHORTCUT_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, shortcutControllerConfigurationAttributes);
    }

    public ShortcutControllerDevice() {
    }
}
