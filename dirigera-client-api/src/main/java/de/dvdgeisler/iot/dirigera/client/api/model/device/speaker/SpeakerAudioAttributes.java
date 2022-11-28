package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SpeakerAudioAttributes {
  public String serviceType;
  public String providerType;
  public SpeakerPlayItem playItem;
  public SpeakerPlayItem nextPlayItem;

  public SpeakerAudioAttributes() {
  }

  public SpeakerAudioAttributes(String serviceType, String providerType, SpeakerPlayItem playItem,
      SpeakerPlayItem nextPlayItem) {
    this.serviceType = serviceType;
    this.providerType = providerType;
    this.playItem = playItem;
    this.nextPlayItem = nextPlayItem;
  }
}
