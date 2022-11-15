package de.dvdgeisler.iot.dirigera.client.api.model.device.motionsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceConfigurationDefaultAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.DeviceSet;
import de.dvdgeisler.iot.dirigera.client.api.model.deviceset.Room;

import java.util.List;

public class MotionSensorConfigurationAttributes extends DeviceConfigurationDefaultAttributes {
    public Integer onDuration;
    public MotionSensorConfig sensorConfig;

    public MotionSensorConfigurationAttributes(final String customIcon, final List<DeviceSet> deviceSet, final Boolean isHidden, final Room room, final Integer onDuration, final MotionSensorConfig sensorConfig) {
        super(customIcon, deviceSet, isHidden, room);
        this.onDuration = onDuration;
        this.sensorConfig = sensorConfig;
    }

    public MotionSensorConfigurationAttributes() {
    }
}
