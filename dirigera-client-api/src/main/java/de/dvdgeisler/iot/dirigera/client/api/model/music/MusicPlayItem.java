package de.dvdgeisler.iot.dirigera.client.api.model.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MusicPlayItem {
  public String title;
  public String artist;
  public String album;
  public String imageUrl;
  public Long duration;

  public MusicPlayItem() {
  }

  public MusicPlayItem(String title, String artist, String album, String imageUrl,
                       Long duration) {
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.imageUrl = imageUrl;
    this.duration = duration;
  }
}
