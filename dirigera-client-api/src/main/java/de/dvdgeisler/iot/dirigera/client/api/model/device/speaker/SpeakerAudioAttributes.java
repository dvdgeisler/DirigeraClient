package de.dvdgeisler.iot.dirigera.client.api.model.device.speaker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.dvdgeisler.iot.dirigera.client.api.model.music.MusicPlayItem;
import de.dvdgeisler.iot.dirigera.client.api.model.music.MusicPlayList;

@JsonInclude(Include.NON_NULL)
public class SpeakerAudioAttributes {
    public String serviceType;
    public String providerType;
    public MusicPlayItem playItem;
    public MusicPlayItem nextPlayItem;
    public MusicPlayList playList;

    public SpeakerAudioAttributes() {
    }

    public SpeakerAudioAttributes(final String serviceType, final String providerType, final MusicPlayItem playItem, final MusicPlayItem nextPlayItem, final MusicPlayList playList) {
        this.serviceType = serviceType;
        this.providerType = providerType;
        this.playItem = playItem;
        this.nextPlayItem = nextPlayItem;
        this.playList = playList;
    }
}
