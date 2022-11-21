package de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.light;

import de.dvdgeisler.iot.dirigera.client.mqtt.hass.model.DeviceState;

public class LightStatus {
    public DeviceState state;
    public Integer brightness;
    public LightColorMode color_mode;
    public Integer color_temp;
    public LightColor color;

    public LightStatus(final DeviceState state, final Integer brightness, final LightColorMode color_mode, final Integer color_temp, final LightColor color) {
        this.state = state;
        this.brightness = brightness;
        this.color_mode = color_mode;
        this.color_temp = color_temp;
        this.color = color;
    }

    public LightStatus() {
    }
}
