package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;

public class LightAttributes extends DeviceAttributes {
    public String productCode;

    @JsonUnwrapped
    public LightStateAttributes state;

    public LightAttributes() {
    }

    public LightAttributes(final String model, final String manufacturer, final String firmwareVersion, final String hardwareVersion, final String serialNumber, final String productCode, final LightStateAttributes state) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber);
        this.productCode = productCode;
        this.state = state;
    }
}
