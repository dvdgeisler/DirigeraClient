package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
public class SpeakerPlaybackPosition {
  public Long position;
  public LocalDateTime timestamp;

  public SpeakerPlaybackPosition(final Long position, final LocalDateTime timestamp) {
    this.position = position;
    this.timestamp = timestamp;
  }

  public SpeakerPlaybackPosition() {
  }
}
