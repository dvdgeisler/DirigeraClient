package de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCapabilities;

import java.time.LocalDateTime;
import java.util.List;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceCategory.SENSOR;
import static de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceType.ENVIRONMENT_SENSOR;

public class EnvironmentSensorDevice extends Device<EnvironmentSensorAttributes, EnvironmentSensorConfigurationAttributes> {

    public EnvironmentSensorDevice(
            final String id,
            final LocalDateTime createdAt,
            final Boolean isReachable,
            final LocalDateTime lastSeen,
            final EnvironmentSensorAttributes attributes,
            final DeviceCapabilities capabilities,
            final List<String> remoteLinks,
            final EnvironmentSensorConfigurationAttributes environmentSensorConfigurationAttributes
    ) {
        super(id, SENSOR, ENVIRONMENT_SENSOR, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, environmentSensorConfigurationAttributes);
    }

    public EnvironmentSensorDevice() {
    }
}
