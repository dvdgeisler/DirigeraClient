package de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.model.device.ota.OtaAttributes;

public class EnvironmentSensorAttributes extends DeviceAttributes<EnvironmentSensorStateAttributes> {
    public String productCode;
    public Boolean permittingJoin;
    public Integer currentTemperature;
    public Integer currentRH;
    public Integer currentPM25;
    public Integer maxMeasuredPM25;
    public Integer minMeasuredPM25;
    public Integer vocIndex;

    @JsonUnwrapped
    public OtaAttributes ota;

    public EnvironmentSensorAttributes() {
    }

    public EnvironmentSensorAttributes(
            final String model,
            final String manufacturer,
            final String firmwareVersion,
            final String hardwareVersion,
            final String serialNumber,
            final String productCode,
            final Boolean permittingJoin,
            final EnvironmentSensorStateAttributes state,
            final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.permittingJoin = permittingJoin;
        this.ota = ota;
    }
}
