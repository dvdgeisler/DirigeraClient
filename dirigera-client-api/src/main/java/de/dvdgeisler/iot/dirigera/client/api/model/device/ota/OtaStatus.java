package de.dvdgeisler.iot.dirigera.client.api.model.device.ota;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OtaStatus {
    @JsonProperty("upToDate")
    UP_TO_DATE,
    @JsonProperty("updateAvailable")
    UPDATE_AVAILABLE
}
