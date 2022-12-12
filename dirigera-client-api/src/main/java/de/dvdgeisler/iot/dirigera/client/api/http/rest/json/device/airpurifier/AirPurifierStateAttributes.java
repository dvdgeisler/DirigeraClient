package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.airpurifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirPurifierStateAttributes extends DeviceStateAttributes {
    public AirPurifierFanMode fanMode;
    public AirPurifierFanModeSequence fanModeSequence;
    public Integer motorState;
    public Boolean childLock;
    public Boolean statusLight;

    public AirPurifierStateAttributes(
            final String customName,
            final AirPurifierFanMode fanMode,
            final AirPurifierFanModeSequence fanModeSequence,
            final Integer motorState,
            final Boolean childLock,
            final Boolean statusLight) {
        super(customName);
        this.fanMode = fanMode;
        this.fanModeSequence = fanModeSequence;
        this.motorState = motorState;
        this.childLock = childLock;
        this.statusLight = statusLight;
    }

    public AirPurifierStateAttributes() {
    }
}
