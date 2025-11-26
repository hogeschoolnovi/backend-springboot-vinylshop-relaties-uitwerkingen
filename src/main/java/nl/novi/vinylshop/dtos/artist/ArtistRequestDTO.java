package nl.novi.vinylshop.dtos.artist;

public class ArtistRequestDTO {
    private String name;
    private String biography;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
