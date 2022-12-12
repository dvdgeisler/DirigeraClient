package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.speaker;

import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceAttributes;
import java.time.LocalDateTime;

public class SpeakerAttributes extends DeviceAttributes<SpeakerStateAttributes> {
    public String productCode;
    public Integer identifyPeriod;
    public LocalDateTime identifyStarted;
    public String audioGroup;
    public LocalDateTime playbackLastChangedTimestamp;

    public SpeakerAttributes() {
    }

    public SpeakerAttributes(final String model, final String manufacturer, final String firmwareVersion, final String hardwareVersion, final String serialNumber, final SpeakerStateAttributes state, final String productCode, final Integer identifyPeriod, final LocalDateTime identifyStarted, final String audioGroup, final LocalDateTime playbackLastChangedTimestamp) {
        super(model, manufacturer, firmwareVersion, hardwareVersion, serialNumber, state);
        this.productCode = productCode;
        this.identifyPeriod = identifyPeriod;
        this.identifyStarted = identifyStarted;
        this.audioGroup = audioGroup;
        this.playbackLastChangedTimestamp = playbackLastChangedTimestamp;
    }
}
