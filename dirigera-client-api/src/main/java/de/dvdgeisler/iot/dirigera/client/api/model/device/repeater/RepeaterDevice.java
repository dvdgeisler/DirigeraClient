package de.dvdgeisler.iot.dirigera.client.api.model.device.repeater;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.time.LocalDateTime;
import java.util.List;


public class RepeaterDevice extends Device<OutletAttributes> {
    public Room room;
    public Boolean isHidden;

    public RepeaterDevice(
            final String id,
            final LocalDateTime createdAt,
            final Boolean isReachable,
            final LocalDateTime lastSeen,
            final OutletAttributes attributes,
            final DeviceCapabilities capabilities,
            final List<DeviceSet> deviceSet,
            final List<String> remoteLinks,
            final Room room,
            final Boolean isHidden) {
        super(id, DeviceCategory.OUTLET, DeviceType.OUTLET, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks);
        this.room = room;
        this.isHidden = isHidden;
    }

    public RepeaterDevice() {
    }
}
