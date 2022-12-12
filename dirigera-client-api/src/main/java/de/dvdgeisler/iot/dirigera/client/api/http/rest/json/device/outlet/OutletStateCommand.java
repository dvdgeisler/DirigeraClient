package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.outlet;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateCommand;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutletStateCommand extends DeviceStateCommand<OutletStateAttributes> {

    public OutletStateCommand(final Integer transitionTime, final Integer period, final OutletStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public OutletStateCommand() {
    }
}
