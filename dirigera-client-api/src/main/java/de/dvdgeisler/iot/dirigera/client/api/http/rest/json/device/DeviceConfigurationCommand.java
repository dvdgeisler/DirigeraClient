package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceConfigurationCommand<_Attributes extends DeviceConfigurationAttributes> {
    @JsonUnwrapped
    public _Attributes attributes;

    public DeviceConfigurationCommand(final _Attributes attributes) {
        this.attributes = attributes;
    }

    public DeviceConfigurationCommand() {
    }
}
