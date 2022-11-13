package de.dvdgeisler.iot.dirigera.client.api.model.device.repeater;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceState;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepeaterState extends DeviceState<RepeaterStateAttributes> {

    public RepeaterState(final Integer transitionTime, final Integer period, final RepeaterStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public RepeaterState() {
    }
}
