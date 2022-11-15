package de.dvdgeisler.iot.dirigera.client.api.model.device.soundcontroller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

public class SoundControllerAttributes extends DeviceAttributes<SoundControllerStateAttributes> {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean isOn;
    public Integer lightLevel;
    public Boolean permittingJoin;

    @JsonUnwrapped
    public OtaAttributes ota;

    public SoundControllerAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final String productCode,
            final Integer batteryPercentage,
            final Boolean isOn,
            final Integer lightLevel,
            final Boolean permittingJoin,
            final SoundControllerStateAttributes state,
            final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.isOn = isOn;
        this.lightLevel = lightLevel;
        this.permittingJoin = permittingJoin;
        this.ota = ota;
    }

    public SoundControllerAttributes() {
    }
}
