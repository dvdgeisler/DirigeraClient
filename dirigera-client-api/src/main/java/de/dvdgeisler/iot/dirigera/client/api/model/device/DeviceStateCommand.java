package de.dvdgeisler.iot.dirigera.client.api.model.device;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceStateCommand<_Attributes extends DeviceStateAttributes> {
    public Integer transitionTime;
    public Integer period;
    public _Attributes attributes;

    public DeviceStateCommand(final Integer transitionTime, final Integer period, final _Attributes attributes) {
        this.transitionTime = transitionTime;
        this.period = period;
        this.attributes = attributes;
    }

    public DeviceStateCommand() {
    }
}
