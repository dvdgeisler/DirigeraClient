package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutletStateAttributes extends DeviceStateAttributes {
    public Boolean isOn;
    public Integer lightLevel;

    public OutletStateAttributes(final String customName, final Boolean isOn, final Integer lightLevel) {
        super(customName);
        this.isOn = isOn;
        this.lightLevel = lightLevel;
    }

    public OutletStateAttributes() {
    }
}
