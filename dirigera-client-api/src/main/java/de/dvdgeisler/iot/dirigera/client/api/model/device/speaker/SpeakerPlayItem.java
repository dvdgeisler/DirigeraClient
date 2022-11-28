package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SpeakerPlayItem {
  public String title;
  public String artist;
  public String album;
  public String imageUrl;
  public Long duration;

  public SpeakerPlayItem() {
  }

  public SpeakerPlayItem(String title, String artist, String album, String imageUrl,
      Long duration) {
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.imageUrl = imageUrl;
    this.duration = duration;
  }
}
