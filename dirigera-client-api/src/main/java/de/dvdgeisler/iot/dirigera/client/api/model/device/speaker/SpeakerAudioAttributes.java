package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.dvdgeisler.iot.dirigera.client.api.model.music.MusicPlayItem;

@JsonInclude(Include.NON_NULL)
public class SpeakerAudioAttributes {
  public String serviceType;
  public String providerType;
  public MusicPlayItem playItem;
  public MusicPlayItem nextPlayItem;

  public SpeakerAudioAttributes() {
  }

  public SpeakerAudioAttributes(String serviceType, String providerType, MusicPlayItem playItem,
      MusicPlayItem nextPlayItem) {
    this.serviceType = serviceType;
    this.providerType = providerType;
    this.playItem = playItem;
    this.nextPlayItem = nextPlayItem;
  }
}
