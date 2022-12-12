package de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.speaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.device.DeviceStateAttributes;
import de.dvdgeisler.iot.dirigera.client.api.http.rest.json.music.MusicPlaybackState;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpeakerStateAttributes extends DeviceStateAttributes {
  public MusicPlaybackState playback;
  public SpeakerAudioAttributes playbackAudio;
  public SpeakerPlaybackPosition playbackPosition;
  public Integer volume;
  public Boolean isMuted;
}
