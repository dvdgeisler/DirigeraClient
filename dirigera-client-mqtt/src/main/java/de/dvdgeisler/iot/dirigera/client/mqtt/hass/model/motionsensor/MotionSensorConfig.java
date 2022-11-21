package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.motionsensor;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotionSensorConfig {
    public String object_id;
    public String unique_id;
    public String name;
    public Device device;
    public String payload_on;
    public String payload_off;
    public String state_topic;

    public DeviceAvailability availability;

    public MotionSensorConfig(
            final String object_id,
            final String unique_id,
            final String name,
            final Device device,
            final String payload_on,
            final String payload_off,
            final String state_topic,
            final DeviceAvailability availability) {
        this.object_id = object_id;
        this.unique_id = unique_id;
        this.name = name;
        this.device = device;
        this.payload_on = payload_on;
        this.payload_off = payload_off;
        this.state_topic = state_topic;
        this.availability = availability;
    }

    public MotionSensorConfig() {
    }
}
