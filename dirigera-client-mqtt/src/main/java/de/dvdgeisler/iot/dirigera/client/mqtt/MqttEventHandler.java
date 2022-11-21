package de.dvdgeisler.iot.dirigera.client.mqtt;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;

import java.util.stream.Stream;

public interface MqttEventHandler<_Device extends Device> {

    Stream<MqttBridgeMessage<_Device>> addDevice(final MqttBridge mqtt, final DirigeraApi api, final _Device device);

    Stream<MqttBridgeMessage<_Device>> updateDevice(final MqttBridge mqtt, final DirigeraApi api, final _Device device);

    Stream<MqttBridgeMessage<_Device>> removeDevice(final MqttBridge mqtt, final DirigeraApi api, final _Device device);
}
