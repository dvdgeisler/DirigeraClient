package de.dvdgeisler.iot.dirigera.client.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dvdgeisler.iot.dirigera.client.api.DirigeraApi;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.Device;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.events.*;
import org.eclipse.paho.client.mqttv3.MqttClient;

public abstract class MqttDeviceEventHandler<D extends Device> extends MqttEventHandler<DeviceEvent> {
    private final Class<D> deviceType;

    public MqttDeviceEventHandler(
            final MqttClient mqtt,
            final DirigeraApi api,
            final Class<D> deviceType,
            final ObjectMapper objectMapper) {
        super(mqtt, api, DeviceEvent.class, objectMapper);
        this.deviceType = deviceType;
    }

    @Override
    protected void onDirigeraEvent(final DeviceEvent event) {
        if(!this.deviceType.isInstance(event.data))
            return;
        this.onDeviceEvent(event);
        if(event instanceof DeviceAddedEvent)
            this.onDeviceEvent((DeviceAddedEvent<D>) event);
        else if(event instanceof DeviceConfigurationChangedEvent)
            this.onDeviceEvent((DeviceConfigurationChangedEvent<D>) event);
        else if(event instanceof DeviceRemovedEvent)
            this.onDeviceEvent((DeviceRemovedEvent<D>) event);
        else if(event instanceof DeviceStateChangedEvent)
            this.onDeviceEvent((DeviceStateChangedEvent<D>) event);
    }

    protected void onDeviceEvent(final DeviceEvent<D> event) {};

    protected void onDeviceEvent(final DeviceAddedEvent<D> event) {};

    protected void onDeviceEvent(final DeviceConfigurationChangedEvent<D> event) {};

    protected void onDeviceEvent(final DeviceStateChangedEvent<D> event) {};

    protected void onDeviceEvent(final DeviceRemovedEvent<D> event) {};
}
