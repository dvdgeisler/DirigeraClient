package de.dvdgeisler.iot.dirigera.client.api.model.music;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MusicPlaybackState {
    @JsonProperty("playbackIdle")
    IDLE,
    @JsonProperty("playbackPlaying")
    PLAYING,
    @JsonProperty("playbackPaused")
    PAUSED,
    @JsonProperty("playbackNext")
    NEXT,
    @JsonProperty("playbackPrevious")
    PREVIOUS,
    @JsonProperty("playbackBuffering")
    BUFFERING
}
