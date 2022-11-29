package de.dvdgeisler.iot.dirigera.client.api.model.music;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MusicPlayItem {
  public String id;
  public String title;
  public String artist;
  public String album;
  public String imageUrl;
  public Long duration;

  public MusicPlayItem() {
  }

  public MusicPlayItem(final String id, final String title, final String artist, final String album, final String imageUrl, final Long duration) {
    this.id = id;
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.imageUrl = imageUrl;
    this.duration = duration;
  }
}
