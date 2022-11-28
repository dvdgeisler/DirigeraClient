package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SpeakerPlaybackState {
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
