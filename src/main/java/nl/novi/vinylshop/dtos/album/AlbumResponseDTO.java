package nl.novi.vinylshop.dtos.album;


import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.dtos.publisher.PublisherResponseDTO;

public class AlbumResponseDTO {
    private Long id;
    private String title;
    private int publishedYear;
    private GenreResponseDTO genre;
    private PublisherResponseDTO publisher;

    public GenreResponseDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreResponseDTO genre) {
        this.genre = genre;
    }

    public PublisherResponseDTO getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherResponseDTO publisher) {
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
