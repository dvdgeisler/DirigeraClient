package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceConfigurationDefaultAttributes;

import java.util.List;

public class SoundControllerConfigurationAttributes extends DeviceConfigurationDefaultAttributes {
    public SoundControllerConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet, final Boolean isHidden, final Room room) {
        super(customIcon, deviceSet, isHidden, room);
    }

    public SoundControllerConfigurationAttributes() {
    }

}
