package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.repeater;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.ota.OtaAttributes;

import java.time.LocalDateTime;

public class RepeaterAttributes extends DeviceAttributes<RepeaterStateAttributes> {
    public String productCode;
    public Boolean permittingJoin;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;

    @JsonUnwrapped
    public OtaAttributes ota;

    public RepeaterAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final String productCode,
            final Boolean permittingJoin,
            final Integer identifyPeriod,
            final LocalDateTime identifyStarted,
            final RepeaterStateAttributes state,
            final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.permittingJoin = permittingJoin;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
        this.ota = ota;
    }

    public RepeaterAttributes() {
    }
}
