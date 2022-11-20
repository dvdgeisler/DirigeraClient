package de.dvdgeisler.iot.dirigera.client.api.model.device.blindscontroller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blinds.BlindsState;
import de.dvdgeisler.iot.dirigera.client.api.model.device.blinds.BlindsStateDeserializer;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

public class BlindsControllerAttributes extends DeviceAttributes<BlindsControllerStateAttributes> {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean permittingJoin;
    public Boolean isOn;
    public Integer lightLevel;
    public Integer blindsCurrentLevel;

    @JsonDeserialize(using = BlindsStateDeserializer.class)
    public BlindsState blindsState;

    @JsonUnwrapped
    public OtaAttributes ota;

    public BlindsControllerAttributes(final String model, final String manufacturer, final String firmwareVersion, final String hardwareVersion, final String serialNumber, final BlindsControllerStateAttributes state, final String productCode, final Integer batteryPercentage, final Boolean permittingJoin, final Boolean isOn, final Integer lightLevel, final Integer blindsCurrentLevel, final BlindsState blindsState, final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.permittingJoin = permittingJoin;
        this.isOn = isOn;
        this.lightLevel = lightLevel;
        this.blindsCurrentLevel = blindsCurrentLevel;
        this.blindsState = blindsState;
        this.ota = ota;
    }

    public BlindsControllerAttributes() {
    }
}
