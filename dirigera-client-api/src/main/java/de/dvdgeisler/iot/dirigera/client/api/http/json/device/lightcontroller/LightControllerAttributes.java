package de.dvdgeisler.iot.dirigera.client.api.http.json.device.lightcontroller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.ota.OtaAttributes;

public class LightControllerAttributes extends DeviceAttributes {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean permittingJoin;
    public Boolean isOn;
    public Integer lightLevel;

    @JsonUnwrapped
    public LightControllerStateAttributes state;

    @JsonUnwrapped
    public OtaAttributes ota;

    public LightControllerAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final String productCode,
            final Integer batteryPercentage,
            final Boolean permittingJoin,
            final Boolean isOn,
            final Integer lightLevel,
            final OtaAttributes ota,
            final LightControllerStateAttributes stateAttributes) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber);
        this.lightLevel = lightLevel;
        this.isOn = isOn;
        this.state = stateAttributes;
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.permittingJoin = permittingJoin;
        this.ota = ota;
    }

    public LightControllerAttributes() {
    }
}
