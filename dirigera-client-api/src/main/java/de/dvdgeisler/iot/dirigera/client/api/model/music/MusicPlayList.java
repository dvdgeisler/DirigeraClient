package de.dvdgeisler.iot.dirigera.client.api.model.music;

import java.util.List;

public class MusicPlayList {
    public String id;
    public List<MusicPlayItem> playItems;
    public String title;

    public MusicPlayList(final String id, final List<MusicPlayItem> playItems, final String title) {
        this.id = id;
        this.playItems = playItems;
        this.title = title;
    }

    public MusicPlayList() {
    }
}
