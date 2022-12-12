package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;

import java.util.List;

/**
 * Provides default configuration attributes that apply to most devices
 */
public class DeviceConfigurationDefaultAttributes extends DeviceConfigurationAttributes {
    public Boolean isHidden;
    public Room room;

    public DeviceConfigurationDefaultAttributes() {
    }

    public DeviceConfigurationDefaultAttributes(final String customIcon, final List<DeviceSet> deviceSet, final Boolean isHidden, final Room room) {
        super(customIcon, deviceSet);
        this.isHidden = isHidden;
        this.room = room;
    }
}
