package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceState;

import static de.dvdgeisler.iot.dirigera.client.api.model.device.light.LightColorAttributes.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LightState extends DeviceState<LightStateAttributes> {
    public final static LightState LIGHT_OFF = new LightState(0, 0, new LightStateAttributes(null, false, null, null));
    public final static LightState LIGHT_ON = new LightState(0, 0, new LightStateAttributes(null, true, null, null));
    public final static LightState LIGHT_LEVEL_0 = new LightState(0, 0, new LightStateAttributes(null, null, 0, null));
    public final static LightState LIGHT_LEVEL_10 = new LightState(0, 0, new LightStateAttributes(null, null, 10, null));
    public final static LightState LIGHT_LEVEL_20 = new LightState(0, 0, new LightStateAttributes(null, null, 20, null));
    public final static LightState LIGHT_LEVEL_30 = new LightState(0, 0, new LightStateAttributes(null, null, 30, null));
    public final static LightState LIGHT_LEVEL_40 = new LightState(0, 0, new LightStateAttributes(null, null, 40, null));
    public final static LightState LIGHT_LEVEL_50 = new LightState(0, 0, new LightStateAttributes(null, null, 50, null));
    public final static LightState LIGHT_LEVEL_60 = new LightState(0, 0, new LightStateAttributes(null, null, 60, null));
    public final static LightState LIGHT_LEVEL_70 = new LightState(0, 0, new LightStateAttributes(null, null, 70, null));
    public final static LightState LIGHT_LEVEL_80 = new LightState(0, 0, new LightStateAttributes(null, null, 80, null));
    public final static LightState LIGHT_LEVEL_90 = new LightState(0, 0, new LightStateAttributes(null, null, 90, null));
    public final static LightState LIGHT_LEVEL_100 = new LightState(0, 0, new LightStateAttributes(null, null, 100, null));
    public final static LightState LIGHT_COLOR_RED = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_RED));
    public final static LightState LIGHT_COLOR_GREEN = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_GREEN));
    public final static LightState LIGHT_COLOR_BLUE = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_BLUE));
    public final static LightState LIGHT_COLOR_WHITE = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_WHITE));

    public final static LightState LIGHT_TEMPERATURE_2000 = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_TEMPERATURE_2000));
    public final static LightState LIGHT_TEMPERATURE_2500 = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_TEMPERATURE_2500));
    public final static LightState LIGHT_TEMPERATURE_3000 = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_TEMPERATURE_3000));
    public final static LightState LIGHT_TEMPERATURE_3500 = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_TEMPERATURE_3500));
    public final static LightState LIGHT_TEMPERATURE_4000 = new LightState(0, 0, new LightStateAttributes(null, null, null, COLOR_TEMPERATURE_4000));

    public LightState(final Integer transitionTime, final Integer period, final LightStateAttributes stateAttributes) {
        super(transitionTime, period, stateAttributes);
    }

    public LightState() {
    }
}
