package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightStateAttributes extends DeviceStateAttributes {
    public Boolean isOn;
    public Integer lightLevel;
    @JsonUnwrapped
    public LightColorAttributes color;

    public LightStateAttributes(final String customName, final Boolean isOn, final Integer lightLevel, final LightColorAttributes color) {
        super(customName);
        this.isOn = isOn;
        this.lightLevel = lightLevel;
        this.color = color;
    }

    public LightStateAttributes() {
    }
}
