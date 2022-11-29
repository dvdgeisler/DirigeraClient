package de.dvdgeisler.iot.dirigera.client.api.model.music;

public class MusicFavorite {
    public String id;
    public String imageUrl;
    public String title;
    public String type;

    public MusicFavorite(final String id, final String imageUrl, final String title, final String type) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.type = type;
    }

    public MusicFavorite() {
    }
}
