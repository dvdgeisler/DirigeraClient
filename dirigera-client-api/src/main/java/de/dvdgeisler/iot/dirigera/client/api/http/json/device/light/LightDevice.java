package de.dvdgeisler.iot.dirigera.client.api.http.json.device.light;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceType;

import java.time.LocalDateTime;
import java.util.List;



public class LightDevice extends Device<LightAttributes> {
    public Room room;
    public Boolean isHidden;

    public LightDevice() {
    }

    public LightDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final LightAttributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks, final Room room, final Boolean isHidden) {
        super(id, DeviceCategory.LIGHT, DeviceType.LIGHT, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks);
        this.room = room;
        this.isHidden = isHidden;
    }
}
