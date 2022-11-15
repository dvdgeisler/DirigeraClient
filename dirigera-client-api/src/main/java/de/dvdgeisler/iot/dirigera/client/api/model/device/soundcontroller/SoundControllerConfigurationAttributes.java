package de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceConfigurationDefaultAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.util.List;

public class SoundControllerConfigurationAttributes extends DeviceConfigurationDefaultAttributes {
    public SoundControllerConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet, final Boolean isHidden, final Room room) {
        super(customIcon, deviceSet, isHidden, room);
    }

    public SoundControllerConfigurationAttributes() {
    }

}
