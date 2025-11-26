package nl.novi.vinylshop.dtos.album;


import jakarta.validation.constraints.*;

public class AlbumRequestDTO {
    @NotBlank(message = "Naam mag niet leeg zijn")
    @Size(min = 3, max = 100, message = "Naam moet tussen 3 en 100 karakters lang zijn")
    private String title;
    @NotNull
    @Min(1877)
    @Max(2100)
    private int publishedYear;
    @NotNull
    private long genreId;
    private long publisherId;

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    // Getters en setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }
}


