package de.dvdgeisler.iot.dirigera.client.api.model.device.shortcutcontroller;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

public class ShortcutControllerAttributes extends DeviceAttributes {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean permittingJoin;
    public Boolean isOn;
    public Integer lightLevel;

    @JsonUnwrapped
    public ShortcutControllerStateAttributes state;

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
            final ShortcutControllerStateAttributes stateAttributes) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber);
        this.lightLevel = lightLevel;
        this.isOn = isOn;
        this.state = stateAttributes;
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.permittingJoin = permittingJoin;
        this.ota = ota;
    }

    public ShortcutControllerAttributes() {
    }
}
