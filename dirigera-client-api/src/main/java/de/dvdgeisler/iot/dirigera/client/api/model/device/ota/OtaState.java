package de.dvdgeisler.iot.dirigera.client.api.model.device.ota;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OtaState {
    @JsonProperty("readyToCheck")
    READY_TO_CHECK,
    @JsonProperty("updateFailed")
    UPDATE_FAILED,
    @JsonProperty("updateInProgress")
    UPDATE_IN_PROGRESS,
    @JsonProperty("readyToUpdate")
    READY_TO_UPDATE,
    @JsonProperty("checkInProgress")
    CHECK_IN_PROGRESS,
    @JsonProperty("checkFailed")
    CHECK_FAILED,
    @JsonProperty("readyToDownload")
    READY_TO_DOWNLOAD,
    @JsonProperty("downloadInProgress")
    DOWNLOAD_IN_PROGRESS,
    @JsonProperty("downloadFailed")
    DOWNLOAD_FAILED,
    @JsonProperty("updateComplete")
    UPDATE_COMPLETE,
    @JsonProperty("batteryCheckFailed")
    BATTERY_CHECK_FAILED
}
