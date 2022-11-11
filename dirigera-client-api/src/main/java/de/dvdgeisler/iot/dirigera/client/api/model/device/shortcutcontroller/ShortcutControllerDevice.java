package de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType.SHORTCUT_CONTROLLER;

public class ShortcutControllerDevice extends Device<ShortcutControllerAttributes> {
    public Room room;
    public Boolean isHidden;

    public ShortcutControllerDevice(
            final String id,
            final LocalDateTime createdAt,
            final Boolean isReachable,
            final LocalDateTime lastSeen,
            final ShortcutControllerAttributes attributes,
            final DeviceCapabilities capabilities,
            final List<DeviceSet> deviceSet,
            final List<String> remoteLinks,
            final Room room,
            final Boolean isHidden) {
        super(id, CONTROLLER, SHORTCUT_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks);
        this.room = room;
        this.isHidden = isHidden;
    }

    public ShortcutControllerDevice() {
    }
}
