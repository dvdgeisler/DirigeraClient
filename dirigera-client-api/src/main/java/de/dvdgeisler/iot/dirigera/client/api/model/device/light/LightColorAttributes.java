package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightColorAttributes {
    public static final LightColorAttributes COLOR_RED = new LightColorAttributes(0.0f,1.0f,null, null, null, null);
    public static final LightColorAttributes COLOR_GREEN = new LightColorAttributes(120.0f,1.0f,null, null, null, null);
    public static final LightColorAttributes COLOR_BLUE = new LightColorAttributes(240.0f,1.0f,null, null, null, null);
    public static final LightColorAttributes COLOR_WHITE = new LightColorAttributes(0.0f,0.0f,null, null, null, null);
    public static final LightColorAttributes COLOR_TEMPERATURE_2000 = new LightColorAttributes(null, null,2000,null,null,null);
    public static final LightColorAttributes COLOR_TEMPERATURE_2500 = new LightColorAttributes(null, null,2500,null,null,null);
    public static final LightColorAttributes COLOR_TEMPERATURE_3000 = new LightColorAttributes(null, null,3000,null,null,null);
    public static final LightColorAttributes COLOR_TEMPERATURE_3500 = new LightColorAttributes(null, null,3500,null,null,null);
    public static final LightColorAttributes COLOR_TEMPERATURE_4000 = new LightColorAttributes(null, null,4000,null,null,null);
    @JsonProperty("colorHue")
    public Float hue;
    @JsonProperty("colorSaturation")
    public Float saturation;
    @JsonProperty("colorTemperature")
    public Integer temperature;
    @JsonProperty("colorTemperatureMin")
    public Integer temperatureMin;
    @JsonProperty("colorTemperatureMax")
    public Integer temperatureMax;
    @JsonProperty("colorMode")
    public LightColorMode mode;

    public LightColorAttributes() {
    }

    public LightColorAttributes(final Float hue, final Float saturation, final Integer temperature, final Integer temperatureMin, final Integer temperatureMax, final LightColorMode mode) {
        this.hue = hue;
        this.saturation = saturation;
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.mode = mode;
    }
}
