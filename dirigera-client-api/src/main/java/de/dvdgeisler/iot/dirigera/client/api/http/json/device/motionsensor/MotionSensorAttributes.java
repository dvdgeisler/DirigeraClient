package de.dvdgeisler.iot.dirigera.client.api.http.json.device.motionsensor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.DeviceAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.json.device.ota.OtaAttributes;

public class MotionSensorAttributes extends DeviceAttributes {
    public String productCode;
    public Integer batteryPercentage;
    public Boolean isOn;
    public Integer lightLevel;
    public Boolean permittingJoin;
    public MotionSensorConfig sensorConfig;
    @JsonUnwrapped
    public MotionSensorStateAttributes state;
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
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber);
        this.state = state;
        this.productCode = productCode;
        this.batteryPercentage = batteryPercentage;
        this.isOn = isOn;
        this.lightLevel = lightLevel;
        this.permittingJoin = permittingJoin;
        this.sensorConfig = sensorConfig;
        this.ota = ota;
    }
}
