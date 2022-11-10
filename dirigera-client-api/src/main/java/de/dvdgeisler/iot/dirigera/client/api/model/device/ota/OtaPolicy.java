package de.dvdgeisler.iot.dirigera.client.api.model.device.ota;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OtaPolicy {
    @JsonProperty("autoDownload")
    AUTO_DOWNLOAD,
    @JsonProperty("autoUpdate")
    AUTO_UPDATE
}
