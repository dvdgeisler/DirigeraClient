package de.dvdgeisler.iot.dirigera.client.api.http.json.music;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MusicPlaybackState {
    @JsonProperty("idle")
    IDLE,
    @JsonProperty("playing")
    PLAYING,
    @JsonProperty("paused")
    PAUSED,
    @JsonProperty("next")
    NEXT,
    @JsonProperty("previous")
    PREVIOUS,
    @JsonProperty("buffering")
    BUFFERING
}
