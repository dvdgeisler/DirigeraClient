package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateCommand;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepeaterStateCommand extends DeviceStateCommand<RepeaterStateAttributes> {

    public RepeaterStateCommand(final Integer transitionTime, final Integer period, final RepeaterStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public RepeaterStateCommand() {
    }
}
