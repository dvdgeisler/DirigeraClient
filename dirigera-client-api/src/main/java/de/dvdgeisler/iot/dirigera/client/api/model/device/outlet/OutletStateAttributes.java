package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightColorAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutletStateAttributes {
    public String customName;
    public Boolean isOn;
    public Integer lightLevel;

    public OutletStateAttributes(final String customName, final Boolean isOn, final Integer lightLevel) {
        this.customName = customName;
        this.isOn = isOn;
        this.lightLevel = lightLevel;
    }

    public OutletStateAttributes() {
    }
}
