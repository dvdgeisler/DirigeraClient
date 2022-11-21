package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model;

import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridge;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState.OFFLINE;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailabilityState.ONLINE;

public class DeviceUtils {

    public static String getDefaultName(final de.dvdgeisler.iot.dirigera.client.api.model.device.Device device) {
        return Optional.ofNullable(device.attributes.state.customName)
                .filter(n -> !n.isBlank())
                .orElse(device.attributes.model);
    }

    public static Device getDeviceConfig(final MqttBridge mqtt, final de.dvdgeisler.iot.dirigera.client.api.model.device.Device device) {
        return new de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device(
                List.of(device.attributes.serialNumber),
                device.attributes.manufacturer,
                device.attributes.model,
                device.attributes.hardwareVersion,
                device.attributes.firmwareVersion,
                getDefaultName(device),
                mqtt.getClientId());
    }

    public static boolean canReceive(final de.dvdgeisler.iot.dirigera.client.api.model.device.Device device, final String... capabilities) {
        return Optional.of(device)
                .map(d->d.capabilities)
                .map(d->d.canReceive)
                .filter(d-> Arrays.stream(capabilities).anyMatch(d::contains))
                .isPresent();
    }

    public static DeviceAvailabilityState getAvailability(final de.dvdgeisler.iot.dirigera.client.api.model.device.Device device) {
        return Optional.of(device)
                .map(d->d.isReachable)
                .filter(d->d)
                .map(d->ONLINE)
                .orElse(OFFLINE);
    }
}
