package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.light;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateCommand;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightStateCommand extends DeviceStateCommand<LightStateAttributes> {
    public final static LightStateCommand LIGHT_OFF = new LightStateCommand(null, null, new LightStateAttributes(null, false, null, null));
    public final static LightStateCommand LIGHT_ON = new LightStateCommand(null, null, new LightStateAttributes(null, true, null, null));
    public final static LightStateCommand LIGHT_LEVEL_0 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 0, null));
    public final static LightStateCommand LIGHT_LEVEL_10 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 10, null));
    public final static LightStateCommand LIGHT_LEVEL_20 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 20, null));
    public final static LightStateCommand LIGHT_LEVEL_30 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 30, null));
    public final static LightStateCommand LIGHT_LEVEL_40 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 40, null));
    public final static LightStateCommand LIGHT_LEVEL_50 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 50, null));
    public final static LightStateCommand LIGHT_LEVEL_60 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 60, null));
    public final static LightStateCommand LIGHT_LEVEL_70 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 70, null));
    public final static LightStateCommand LIGHT_LEVEL_80 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 80, null));
    public final static LightStateCommand LIGHT_LEVEL_90 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 90, null));
    public final static LightStateCommand LIGHT_LEVEL_100 = new LightStateCommand(null, null, new LightStateAttributes(null, null, 100, null));
    public final static LightStateCommand LIGHT_COLOR_RED = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_RED));
    public final static LightStateCommand LIGHT_COLOR_GREEN = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_GREEN));
    public final static LightStateCommand LIGHT_COLOR_BLUE = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_BLUE));
    public final static LightStateCommand LIGHT_COLOR_WHITE = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_WHITE));
    public final static LightStateCommand LIGHT_TEMPERATURE_2000 = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_TEMPERATURE_2000));
    public final static LightStateCommand LIGHT_TEMPERATURE_2500 = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_TEMPERATURE_2500));
    public final static LightStateCommand LIGHT_TEMPERATURE_3000 = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_TEMPERATURE_3000));
    public final static LightStateCommand LIGHT_TEMPERATURE_3500 = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_TEMPERATURE_3500));
    public final static LightStateCommand LIGHT_TEMPERATURE_4000 = new LightStateCommand(null, null, new LightStateAttributes(null, null, null, LightColorAttributes.COLOR_TEMPERATURE_4000));

    public LightStateCommand(final Integer transitionTime, final Integer period, final LightStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public LightStateCommand() {
    }
}
