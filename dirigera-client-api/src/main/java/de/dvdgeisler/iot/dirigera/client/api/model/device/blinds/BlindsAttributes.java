package de.dvdgeisler.iot.dirigera.client.api.model.device.blinds;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

public class BlindsAttributes extends DeviceAttributes<BlindsStateAttributes> {
    public String productCode;
    public Boolean permittingJoin;
    public Integer batteryPercentage;

    @JsonUnwrapped
    public OtaAttributes ota;

    public BlindsAttributes(final String model, final String manufacturer, final String firmwareVersion, final String hardwareVersion, final String serialNumber, final BlindsStateAttributes state, final String productCode, final Boolean permittingJoin, final Integer batteryPercentage, final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.permittingJoin = permittingJoin;
        this.batteryPercentage = batteryPercentage;
        this.ota = ota;
    }

    public BlindsAttributes() {
    }
}
