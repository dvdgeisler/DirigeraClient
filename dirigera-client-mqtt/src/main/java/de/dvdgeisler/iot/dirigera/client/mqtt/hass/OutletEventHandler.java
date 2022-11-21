package de.dvdgeisler.iot.dirigera.client.mqtt.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.model.device.outlet.OutletDevice;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridge;
import de.dvdgeisler.iot.dirigera.client.mqtt.MqttBridgeMessage;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.outlet.OutletConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Stream;

import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState.OFF;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState.ON;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceUtils.*;
import static de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.outlet.OutletUtils.getState;

@Component
public class OutletEventHandler extends HassEventHandler<OutletDevice, DeviceState> {
    private final static Logger log = LoggerFactory.getLogger(OutletEventHandler.class);
    private final static String HASS_COMPONENT = "switch";
    private final static String TOPIC_CONFIG = "config";
    private final static String TOPIC_STATE = "state";
    private final static String TOPIC_SET = "set";
    private final static String TOPIC_AVAILABILITY = "availability";
    private final static String TOPIC_REMOVE = "remove";

    public OutletEventHandler(
            @Value("${dirigera.mqtt.hass.prefix:homeassistant}") final String prefix,
            final ObjectMapper objectMapper) {
        super(prefix, HASS_COMPONENT, objectMapper, DeviceState.class);
    }

    @Override
    public void setDevice(final MqttBridge mqtt, final DirigeraApi api, final OutletDevice device, final DeviceState status) {
        Optional.ofNullable(status)
                .filter(t -> canReceive(device, "isOn"))
                .map(s -> switch (s) {
                    case ON -> api.device.outlet.turnOn(device);
                    case OFF -> api.device.outlet.turnOff(device);
                })
                .ifPresent(Mono::block);
    }


    @Override
    public Stream<MqttBridgeMessage<OutletDevice>> addDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final OutletDevice device) {

        this.subscribe(mqtt, api, device);
        return Stream.of(this.build(mqtt, device, TOPIC_CONFIG,
                new OutletConfig(
                        device.id,
                        device.id,
                        getDefaultName(device),
                        getDeviceConfig(mqtt, device),
                        this.toJSON(ON),
                        this.toJSON(OFF),
                        this.toJSON(ON),
                        this.toJSON(OFF),
                        this.topic(mqtt, device, TOPIC_SET),
                        this.topic(mqtt, device, TOPIC_STATE),
                        this.getDeviceAvailabilityConfig(mqtt, device, TOPIC_AVAILABILITY))));
    }

    @Override
    public Stream<MqttBridgeMessage<OutletDevice>> updateDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final OutletDevice device) {
        return Stream.of(
                this.build(mqtt, device, TOPIC_STATE, getState(device)),
                this.build(mqtt, device, TOPIC_AVAILABILITY, getAvailability(device)));
    }

    @Override
    public Stream<MqttBridgeMessage<OutletDevice>> removeDevice(
            final MqttBridge mqtt,
            final DirigeraApi api,
            final OutletDevice device) {
        return Stream.of(this.build(mqtt, device, TOPIC_REMOVE, null));
    }
}
