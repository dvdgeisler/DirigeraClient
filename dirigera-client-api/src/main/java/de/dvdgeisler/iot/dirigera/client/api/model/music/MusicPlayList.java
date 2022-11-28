package de.dvdgeisler.iot.dirigera.client.api.model.music;

import de.dvdgeisler.iot.dirigera.client.api.model.device.speaker.SpeakerPlayItem;

import java.util.List;

public class MusicPlayList {
    public String id;
    public List<SpeakerPlayItem> playItems;
    public String title;

    public MusicPlayList(final String id, final List<SpeakerPlayItem> playItems, final String title) {
        this.id = id;
        this.playItems = playItems;
        this.title = title;
    }

    public MusicPlayList() {
    }
}
