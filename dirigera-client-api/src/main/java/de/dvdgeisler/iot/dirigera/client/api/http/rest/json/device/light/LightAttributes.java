package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.light;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStartupMode;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;

import java.time.LocalDateTime;

public class LightAttributes extends DeviceAttributes<LightStateAttributes> {
    public String productCode;

    public DeviceStartupMode startupOnOff;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;

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
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber,state);
        this.productCode = productCode;
        this.startupOnOff = startupOnOff;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
    }
}
