package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.motionsensor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.ota.OtaAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;

public class MotionSensorAttributes extends DeviceAttributes<MotionSensorStateAttributes> {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean isOn;
    public Integer lightLevel;
    public Boolean permittingJoin;
    public MotionSensorConfig sensorConfig;

    @JsonUnwrapped
    public OtaAttributes ota;

    public MotionSensorAttributes() {
    }

    public MotionSensorAttributes(
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
            final MotionSensorConfig sensorConfig,
            final MotionSensorStateAttributes state,
            final OtaAttributes ota) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber,state);
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.isOn = isOn;
        this.lightLevel = lightLevel;
        this.permittingJoin = permittingJoin;
        this.sensorConfig = sensorConfig;
        this.ota = ota;
    }
}
