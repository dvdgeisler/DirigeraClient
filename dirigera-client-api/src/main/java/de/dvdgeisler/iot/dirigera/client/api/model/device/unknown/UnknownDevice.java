package de.dvdgeisler.iot.dirigera.client.api.model.device.unknown;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.dvdgeisler.iot.dirigera.client.api.model.device.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link UnknownDevice} represents a minimal subtype of a device. An {@link UnknownDevice} is provided as the
 * default deserialization strategy in case Jackson reads a json blob whose subtype is not derivable by its
 * {@link Device#deviceType} entry. A corresponding record is created via the {@link UnknownDeviceDeserializer} in the
 * {@link UnknownDeviceCollector}.
 */
@JsonDeserialize(using = UnknownDeviceDeserializer.class)
public class UnknownDevice extends Device<
        DeviceAttributes<DeviceStateAttributes>,
        DeviceConfigurationAttributes> {

    public UnknownDevice(
            final String id,
            final DeviceCategory type,
            final DeviceType deviceType,
            final LocalDateTime createdAt,
            final Boolean isReachable,
            final LocalDateTime lastSeen,
            final DeviceAttributes<DeviceStateAttributes> attributes,
            final DeviceCapabilities capabilities,
            final List<String> remoteLinks,
            final DeviceConfigurationAttributes deviceConfigurationAttributes) {
        super(id, type, deviceType, createdAt, isReachable, lastSeen, attributes, capabilities, remoteLinks, deviceConfigurationAttributes);
    }

    public UnknownDevice() {
    }
}
