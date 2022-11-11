package de.dvdgeisler.iot.dirigera.client.api.model.device.outlet;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceState;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutletState extends DeviceState<OutletStateAttributes> {

    public OutletState(final Integer transitionTime, final Integer period, final OutletStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public OutletState() {
    }
}
