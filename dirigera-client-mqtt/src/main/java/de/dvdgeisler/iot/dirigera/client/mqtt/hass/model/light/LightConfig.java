package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.Device;
import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceAvailability;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightConfig {
    public String object_id;
    public String unique_id;
    public String name;
    public Device device;
    public Boolean brightness;
    public Integer brightness_scale;
    public Boolean color_mode;
    public List<LightColorMode> supported_color_modes;
    public String command_topic;
    public String state_topic;
    public String schema;
    public Integer max_mireds;
    public Integer min_mireds;

    public DeviceAvailability availability;

    public LightConfig(final String object_id, final String unique_id, final String name, final Device device, final Boolean brightness, final Integer brightness_scale, final Boolean color_mode, final List<LightColorMode> supported_color_modes, final String command_topic, final String state_topic, final String schema, final Integer max_mireds, final Integer min_mireds, final DeviceAvailability availability) {
        this.object_id = object_id;
        this.unique_id = unique_id;
        this.name = name;
        this.device = device;
        this.brightness = brightness;
        this.brightness_scale = brightness_scale;
        this.color_mode = color_mode;
        this.supported_color_modes = supported_color_modes;
        this.command_topic = command_topic;
        this.state_topic = state_topic;
        this.schema = schema;
        this.max_mireds = max_mireds;
        this.min_mireds = min_mireds;
        this.availability = availability;
    }

    public LightConfig() {
    }
}
