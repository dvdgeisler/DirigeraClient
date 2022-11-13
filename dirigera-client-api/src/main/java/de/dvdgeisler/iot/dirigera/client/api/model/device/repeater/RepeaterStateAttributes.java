package de.dvdgeisler.iot.dirigera.client.api.model.device.repeater;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepeaterStateAttributes {
    public String customName;

    public RepeaterStateAttributes(final String customName) {
        this.customName = customName;
    }

    public RepeaterStateAttributes() {
    }
}
