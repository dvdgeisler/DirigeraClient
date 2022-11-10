package de.dvdgeisler.iot.dirigera.client.api.http.json.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.http.json.deviceset.Room;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceCategory.SENSOR;
import static de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceType.MOTION_SENSOR;

public class MotionSensorDevice extends Device<MotionSensorAttributes> {
    public Room room;
    public Boolean isHidden;
    public Integer onDuration;
    public MotionSensorConfig sensorConfig;

    public MotionSensorDevice() {
    }

    public MotionSensorDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final MotionSensorAttributes attributes, final DeviceCapabilities capabilities, final List<DeviceSet> deviceSet, final List<String> remoteLinks, final Room room, final Boolean isHidden, final Integer onDuration, final MotionSensorConfig sensorConfig) {
        super(id, SENSOR, MOTION_SENSOR, createdAt, isReachable, lastSeen, attributes, capabilities, deviceSet, remoteLinks);
        this.room = room;
        this.isHidden = isHidden;
        this.onDuration = onDuration;
        this.sensorConfig = sensorConfig;
    }
}
