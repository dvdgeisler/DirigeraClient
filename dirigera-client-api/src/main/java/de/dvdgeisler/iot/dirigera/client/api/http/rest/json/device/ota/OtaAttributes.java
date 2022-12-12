package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.ota;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class OtaAttributes {
    @JsonProperty("otaStatus")
    public OtaStatus status;
    @JsonProperty("otaState")
    public OtaState state;
    @JsonProperty("otaProgress")
    public Integer progress;
    @JsonProperty("otaScheduleStart")
    public LocalTime scheduleStart;
    @JsonProperty("otaScheduleEnd")
    public LocalTime scheduleEnd;
    @JsonProperty("otaPolicy")
    public OtaPolicy policy;

    public OtaAttributes() {
    }

    public OtaAttributes(final OtaStatus status, final OtaState state, final Integer progress, final LocalTime scheduleStart, final LocalTime scheduleEnd, final OtaPolicy policy) {
        this.status = status;
        this.state = state;
        this.progress = progress;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
        this.policy = policy;
    }
}
