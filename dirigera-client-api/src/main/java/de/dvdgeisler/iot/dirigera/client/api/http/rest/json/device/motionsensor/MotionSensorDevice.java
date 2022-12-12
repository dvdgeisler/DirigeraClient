package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCapabilities;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceCategory;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceType;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;

import java.time.LocalDateTime;
import java.util.List;

public class MotionSensorDevice extends Device<MotionSensorAttributes, MotionSensorConfigurationAttributes> {
    public Integer onDuration;
    public MotionSensorConfig sensorConfig;

    public MotionSensorDevice() {
    }

    public MotionSensorDevice(final String id, final LocalDateTime createdAt, final Boolean isReachable, final LocalDateTime lastSeen, final MotionSensorAttributes attributes, final DeviceCapabilities capabilities, final List<String> remoteLinks, final MotionSensorConfigurationAttributes motionSensorConfigurationAttributes, final Integer onDuration, final MotionSensorConfig sensorConfig) {
        super(id, DeviceCategory.SENSOR, DeviceType.MOTION_SENSOR, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, motionSensorConfigurationAttributes);
        this.onDuration = onDuration;
        this.sensorConfig = sensorConfig;
    }
}
