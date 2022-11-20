package de.dvdgeisler.iot.dirigera.client.api.model.device.airpurifier;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirPurifierFilterAttributes {
    @JsonProperty("filterElapsedTime")
    public Integer elapsedTime;
    @JsonProperty("filterAlarmStatus")
    public Boolean alarmStatus;
    @JsonProperty("filterLifetime")
    public Integer lifetime;

    public AirPurifierFilterAttributes(final Integer elapsedTime, final Boolean alarmStatus, final Integer lifetime) {
        this.elapsedTime = elapsedTime;
        this.alarmStatus = alarmStatus;
        this.lifetime = lifetime;
    }
}
