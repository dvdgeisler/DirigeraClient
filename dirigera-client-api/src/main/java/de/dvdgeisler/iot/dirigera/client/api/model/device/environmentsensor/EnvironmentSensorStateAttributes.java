package de.dvdgeisler.iot.dirigera.client.api.model.device.environmentsensor;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceStateAttributes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvironmentSensorStateAttributes extends DeviceStateAttributes {
    public Integer currentTemperature;
    public Integer currentRH;
    public Integer currentPM25;
    public Integer maxMeasuredPM25;
    public Integer minMeasuredPM25;
    public Integer vocIndex;
    public EnvironmentSensorStateAttributes(final String customName, final Integer currentTemperature, final Integer currentRH, final Integer currentPM25,
                                            final Integer maxMeasuredPM25, final Integer minMeasuredPM25, final Integer vocIndex) {
        super(customName);
        this.currentTemperature = currentTemperature;
        this.currentRH = currentRH;
        this.currentPM25 = currentPM25;
        this.maxMeasuredPM25 = maxMeasuredPM25;
        this.minMeasuredPM25 = minMeasuredPM25;
        this.vocIndex = vocIndex;
    }

    public EnvironmentSensorStateAttributes() {
    }
}
