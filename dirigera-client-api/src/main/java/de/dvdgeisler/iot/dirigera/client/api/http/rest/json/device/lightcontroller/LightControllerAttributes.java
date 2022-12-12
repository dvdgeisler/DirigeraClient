package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.lightcontroller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.ota.OtaAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;

public class LightControllerAttributes extends DeviceAttributes<LightControllerStateAttributes> {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean permittingJoin;
    public Boolean isOn;
    public Integer lightLevel;

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
            final LightControllerStateAttributes state) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.lightLevel = lightLevel;
        this.isOn = isOn;
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.permittingJoin = permittingJoin;
        this.ota = ota;
    }

    public LightControllerAttributes() {
    }
}
