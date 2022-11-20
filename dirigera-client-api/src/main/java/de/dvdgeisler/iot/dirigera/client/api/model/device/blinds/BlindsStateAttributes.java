package de.dvdgeisler.iot.dirigera.client.api.model.device.blinds;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlindsStateAttributes extends DeviceStateAttributes {
    public Integer blindsTargetLevel;
    public Integer blindsCurrentLevel;
    public BlindsState blindsState;

    public BlindsStateAttributes(final String customName, final Integer blindsTargetLevel, final Integer blindsCurrentLevel, final BlindsState blindsState) {
        super(customName);
        this.blindsTargetLevel = blindsTargetLevel;
        this.blindsCurrentLevel = blindsCurrentLevel;
        this.blindsState = blindsState;
    }

    public BlindsStateAttributes() {
    }
}
