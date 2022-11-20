package de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStartupMode;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

import java.time.LocalDateTime;

public class AirPurifierAttributes extends DeviceAttributes<AirPurifierStateAttributes> {
    public String productCode;
    public Boolean permittingJoin;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;
    public Integer motorRuntime;
    public Integer currentPM25;

    @JsonUnwrapped
    public AirPurifierFilterAttributes filter;

    @JsonUnwrapped
    public OtaAttributes ota;

    public AirPurifierAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final AirPurifierStateAttributes state,
            final String productCode,
            final Boolean permittingJoin,
            final Integer identifyPeriod,
            final LocalDateTime identifyStarted,
            final Integer motorRuntime,
            final Integer currentPM25,
            final AirPurifierFilterAttributes filter,
            final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.permittingJoin = permittingJoin;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
        this.motorRuntime = motorRuntime;
        this.currentPM25 = currentPM25;
        this.filter = filter;
        this.ota = ota;
    }

    public AirPurifierAttributes() {
    }
}
