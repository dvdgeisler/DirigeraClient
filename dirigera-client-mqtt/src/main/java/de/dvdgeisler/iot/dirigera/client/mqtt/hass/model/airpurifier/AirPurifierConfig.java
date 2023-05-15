package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.airpurifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirPurifierConfig {
    public String object_id;
    public String unique_id;
    public String name;
    public Device device;
    public boolean optimistic;
    public String payload_off;
    public String payload_on;
    public String command_topic;
    public String state_off;
    public String state_on;
    public String state_topic;
    public String value_template;
    public String percentage_state_topic;
    public String percentage_value_template;
    public String preset_mode_command_template;
    public String preset_mode_command_topic;
    public String preset_mode_state_topic;
    public String preset_mode_value_template;
    public String preset_modes;
    public DeviceAvailability availability;

    public AirPurifierConfig(String object_id, String unique_id, String name, Device device, boolean optimistic,
                             String payload_off, String payload_on, String command_topic, String state_off,
                             String state_on, String state_topic, String value_template, String percentage_state_topic,
                             String percentage_value_template, String preset_mode_command_template, String preset_mode_command_topic,
                             String preset_mode_state_topic, String preset_mode_value_template, String preset_modes, DeviceAvailability availability) {
        this.object_id = object_id;
        this.unique_id = unique_id;
        this.name = name;
        this.device = device;
        this.optimistic = optimistic;
        this.payload_off = payload_off;
        this.payload_on = payload_on;
        this.command_topic = command_topic;
        this.state_off = state_off;
        this.state_on = state_on;
        this.state_topic = state_topic;
        this.value_template = value_template;
        this.percentage_state_topic = percentage_state_topic;
        this.percentage_value_template = percentage_value_template;
        this.preset_mode_command_template = preset_mode_command_template;
        this.preset_mode_command_topic = preset_mode_command_topic;
        this.preset_mode_state_topic = preset_mode_state_topic;
        this.preset_mode_value_template = preset_mode_value_template;
        this.preset_modes = preset_modes;
        this.availability = availability;
    }

    public AirPurifierConfig() {
    }
}
