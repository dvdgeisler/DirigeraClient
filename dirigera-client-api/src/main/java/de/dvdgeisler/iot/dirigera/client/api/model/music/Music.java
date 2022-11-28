package de.dvdgeisler.iot.dirigera.client.api.model.music;

import java.util.List;

public class Music {
    public List<MusicPlayList> playlists;
    public List<MusicFavorite> favorites;

    public Music() {
    }

    public Music(final List<MusicPlayList> playlists, final List<MusicFavorite> favorites) {
        this.playlists = playlists;
        this.favorites = favorites;
    }
}
