package de.dvdgeisler.iot.dirigera.client.api.model.device.repeater;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepeaterStateAttributes extends DeviceStateAttributes {

    public RepeaterStateAttributes(final String customName) {
        super(customName);
    }

    public RepeaterStateAttributes() {
    }
}
