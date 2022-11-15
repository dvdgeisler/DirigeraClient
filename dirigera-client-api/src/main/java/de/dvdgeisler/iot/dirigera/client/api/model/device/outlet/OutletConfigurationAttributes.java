package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceConfigurationDefaultAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.util.List;

public class OutletConfigurationAttributes extends DeviceConfigurationDefaultAttributes {
    public Boolean isHidden;
    public Room room;

    public OutletConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet, final Boolean isHidden, final Room room, final Boolean isHidden1, final Room room1) {
        super(customIcon, deviceSet, isHidden, room);
        this.isHidden = isHidden1;
        this.room = room1;
    }

    public OutletConfigurationAttributes() {
    }


}
