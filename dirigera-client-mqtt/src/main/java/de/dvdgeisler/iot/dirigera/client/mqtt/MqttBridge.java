package de.dvdgeisler.iot.dirigera.client.mqtt;

import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.Device;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MqttBridge extends org.eclipse.paho.client.mqttv3.MqttClient {
    private final static Logger log = LoggerFactory.getLogger(MqttBridge.class);

    private final HashMap<Class, List<MqttEventHandler>> eventHandler;
    private final DirigeraApi api;
    private final Map<String, byte[]> repetitionFilter;

    public MqttBridge(
            @Value("${dirigera.mqtt.hostname:localhost}") final String host,
            @Value("${dirigera.mqtt.port:1883}") final Short port,
            @Value("${dirigera.mqtt.username:}") final String username,
            @Value("${dirigera.mqtt.password:}") final String password,
            @Value("${dirigera.mqtt.reconnect:true}") final Boolean reconnect,
            @Value("${dirigera.mqtt.timeout:10}") final Integer timeout,
            final DirigeraApi api) throws MqttException {
        super(String.format("tcp://%s:%d", host, port),
                Objects.requireNonNull(api.status().map(s -> s.id).block()));
        final MqttConnectOptions options;
        this.api = api;
        this.eventHandler = new HashMap<>();
        this.repetitionFilter = new HashMap<>();

        log.debug("Connect to MQTT broker: host={}, port={}, publisherId={}, reconnect={}, timeout={}",
                host, port, this.getClientId(), reconnect, timeout);
        options = new MqttConnectOptions();
        options.setAutomaticReconnect(reconnect);
        options.setCleanSession(true);
        options.setConnectionTimeout(timeout);

        if (!username.isEmpty() && !password.isEmpty()) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }
        this.connect(options);

        log.info("Connection to MQTT broker successfully established");
    }

    private <_Device extends Device> List<MqttEventHandler<_Device>> getEventHandler(final _Device device) {
        return this.eventHandler
                .computeIfAbsent(device.getClass(), key -> new ArrayList<>())
                .stream()
                .map(factory -> (MqttEventHandler<_Device>) factory)
                .toList();
    }

    private <_Device extends Device> void publishMessage(final MqttBridgeMessage<_Device> message) {
        final String messageKey;

        messageKey = message.getDeviceId()+message.getTopic();
        if(message.filterRepetitions() &&
                Arrays.equals(
                        this.repetitionFilter.getOrDefault(messageKey, null),
                        message.getPayload()))
            return; // skip repetition
        this.repetitionFilter.put(messageKey, message.getPayload());

        log.debug("Publish message to MQTT: " +
                        "topic={}, " +
                        "payload={}, " +
                        "factory={}, " +
                        "deviceId={}, " +
                        "deviceType={}, " +
                        "deviceCategory={}",
                message.getTopic(),
                message.getPayloadAsString(),
                message.getFactory().getClass().getSimpleName(),
                message.getDevice().id,
                message.getDevice().deviceType,
                message.getDevice().type);
        try {
            this.publish(message.getTopic(), message.getMessage());
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
    }

    public <_Device extends Device> void registerEventHandler(final Class<_Device> deviceClass, final MqttEventHandler<_Device> eventHandler) {
        log.debug("Register event handler {} for device class {}",
                eventHandler.getClass().getSimpleName(),
                deviceClass.getSimpleName());
        this.eventHandler.computeIfAbsent(deviceClass, key -> new ArrayList<>()).add(eventHandler);
    }

    public <_Device extends Device> void addDevice(final _Device device) {
        log.debug("Create device: id={}, name={}, category={}, type={}",
                device.id, device.attributes.state.customName, device.type, device.deviceType);
        this.getEventHandler(device)
                .stream()
                .flatMap(factory -> factory.addDevice(this, this.api, device))
                .forEach(this::publishMessage);
    }

    public <_Device extends Device> void updateDevice(final _Device device) {
        //log.debug("Update device: id={}, name={}, category={}, type={}",
        //        device.id, device.attributes.state.customName, device.type, device.deviceType);
        this.getEventHandler(device)
                .stream()
                .flatMap(factory -> factory.updateDevice(this, this.api, device))
                .forEach(this::publishMessage);
    }

    public <_Device extends Device> void removeDevice(final _Device device) {
        log.debug("Remove device: id={}, name={}, category={}, type={}",
                device.id, device.attributes.state.customName, device.type, device.deviceType);
        this.getEventHandler(device)
                .stream()
                .flatMap(factory -> factory.removeDevice(this, this.api, device))
                .forEach(this::publishMessage);
    }
}
