package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.blinds;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlindsConfig {
    public String object_id;
    public String unique_id;
    public String name;
    public Device device;
    public String payload_open;
    public String payload_close;
    public String payload_stop;
    public String command_topic;
    public Integer position_open;
    public Integer position_closed;
    public String position_topic;
    public String state_open;
    public String state_opening;
    public String state_stopped;
    public String state_closed;
    public String state_closing;
    public String state_topic;
    public String value_template;
    public String position_template;
    public DeviceAvailability availability;

    public BlindsConfig(final String object_id, final String unique_id, final String name, final Device device, final String payload_open, final String payload_close, final String payload_stop, final String command_topic, final Integer position_open, final Integer position_closed, final String position_topic, final String state_open, final String state_opening, final String state_stopped, final String state_closed, final String state_closing, final String state_topic, final String value_template, final String position_template, final DeviceAvailability availability) {
        this.object_id = object_id;
        this.unique_id = unique_id;
        this.name = name;
        this.device = device;
        this.payload_open = payload_open;
        this.payload_close = payload_close;
        this.payload_stop = payload_stop;
        this.command_topic = command_topic;
        this.position_open = position_open;
        this.position_closed = position_closed;
        this.position_topic = position_topic;
        this.state_open = state_open;
        this.state_opening = state_opening;
        this.state_stopped = state_stopped;
        this.state_closed = state_closed;
        this.state_closing = state_closing;
        this.state_topic = state_topic;
        this.value_template = value_template;
        this.position_template = position_template;
        this.availability = availability;
    }

    public BlindsConfig() {
    }
}
