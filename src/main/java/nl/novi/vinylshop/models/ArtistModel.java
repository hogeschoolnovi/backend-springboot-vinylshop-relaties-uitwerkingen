package nl.novi.vinylshop.models;

import java.util.List;

public class ArtistModel extends BaseModel {

    private String name;
    private String biography;
    private List<AlbumModel> albums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<AlbumModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumModel> albums) {
        this.albums = albums;
    }
}
