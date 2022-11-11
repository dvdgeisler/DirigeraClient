package de.dvdgeisler.iot.dirigera.client.api.model.device.light;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStartupMode;

import java.time.LocalDateTime;

public class LightAttributes extends DeviceAttributes {
    public String productCode;

    public DeviceStartupMode startupOnOff;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;

    @JsonUnwrapped
    public LightStateAttributes state;

    public LightAttributes() {
    }

    public LightAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final String productCode,
            final DeviceStartupMode startupOnOff,
            final Integer identifyPeriod,
            final LocalDateTime identifyStarted,
            final LightStateAttributes state) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber);
        this.productCode = productCode;
        this.startupOnOff = startupOnOff;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
        this.state = state;
    }
}
