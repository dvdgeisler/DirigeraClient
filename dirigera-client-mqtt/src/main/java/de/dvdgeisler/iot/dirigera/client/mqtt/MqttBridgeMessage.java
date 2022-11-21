package de.dvdgeisler.iot.dirigera.client.mqtt;

import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Optional;

public class MqttBridgeMessage<_Device extends Device> {
    private final _Device device;
    private final MqttEventHandler<_Device> factory;
    private final String topic;
    private final MqttMessage message;
    private final boolean filterRepetitions;

    public MqttBridgeMessage(final _Device device, final MqttEventHandler<_Device> factory, final String topic, final MqttMessage message, final boolean filterRepetitions) {
        this.device = device;
        this.factory = factory;
        this.topic = topic;
        this.message = message;
        this.filterRepetitions = filterRepetitions;
    }

    public MqttBridgeMessage(final _Device device, final MqttEventHandler<_Device> factory, final String topic, final MqttMessage message) {
        this(device, factory, topic, message, true);
    }

    public _Device getDevice() {
        return this.device;
    }

    public String getDeviceId() {
        return Optional.ofNullable(this.device).map(d->d.id).orElse(null);
    }

    public MqttEventHandler<_Device> getFactory() {
        return this.factory;
    }

    public String getTopic() {
        return this.topic;
    }

    public MqttMessage getMessage() {
        return this.message;
    }

    public byte[] getPayload() {
        return Optional.ofNullable(this.message).map(MqttMessage::getPayload).orElse(new byte[0]);
    }

    public String getPayloadAsString() {
        return new String(this.getPayload());
    }

    public boolean filterRepetitions() {
        return this.filterRepetitions;
    }
}
