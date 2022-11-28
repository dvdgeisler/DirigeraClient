package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import de.dvdgeisler.iot.dirigera.client.api.model.device.DeviceAttributes;
import java.time.LocalDateTime;

public class SpeakerAttributes extends DeviceAttributes<SpeakerStateAttributes> {
    public String productCode;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;
    public String audioGroup;

    public SpeakerAttributes() {
    }

    public SpeakerAttributes(
        final String model,
        final String manufacturer,
        final String firmwareVersion,
        final String hardwareVersion,
        final String serialNumber,
        final String productCode,
        final Integer identifyPeriod,
        final LocalDateTime identifyStarted,
        final String audioGroup,
        final SpeakerStateAttributes state) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
        this.audioGroup = audioGroup;
    }
}
