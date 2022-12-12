package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.shortcutcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceConfigurationDefaultAttributes;

import java.util.List;

public class ShortcutControllerConfigurationAttributes extends DeviceConfigurationDefaultAttributes {
    public ShortcutControllerConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet, final Boolean isHidden, final Room room) {
        super(customIcon, deviceSet, isHidden, room);
    }

    public ShortcutControllerConfigurationAttributes() {
    }
}
