package de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

public class ShortcutControllerAttributes extends DeviceAttributes<ShortcutControllerStateAttributes> {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean permittingJoin;
    public Boolean isOn;
    public Integer lightLevel;

    @JsonUnwrapped
    public OtaAttributes ota;

    public ShortcutControllerAttributes(
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
            final ShortcutControllerStateAttributes state) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.lightLevel = lightLevel;
        this.isOn = isOn;
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.permittingJoin = permittingJoin;
        this.ota = ota;
    }

    public ShortcutControllerAttributes() {
    }
}
