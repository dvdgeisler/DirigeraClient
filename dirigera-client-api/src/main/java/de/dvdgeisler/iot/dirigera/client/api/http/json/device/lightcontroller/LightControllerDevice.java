package de.dvdgeisler.iot.dirigera.client.api.http.json.device.lightcontroller;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCategory.CONTROLLER;
import static de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceType.LIGHT_CONTROLLER;

public class LightControllerDevice extends Device<LightControllerAttributes> {
    public Room room;
    public Boolean isHidden;

    public LightControllerDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final LightControllerAttributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks, final Room room, final Boolean isHidden) {
        super(id, CONTROLLER, LIGHT_CONTROLLER, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks);
        this.room = room;
        this.isHidden = isHidden;
    }

    public LightControllerDevice() {
    }
}
