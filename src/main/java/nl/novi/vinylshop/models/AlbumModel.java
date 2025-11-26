package nl.novi.vinylshop.models;

import java.util.List;

public class AlbumModel extends BaseModel {

    private String title;
    private int releaseYear;
    private GenreModel genre;
    private PublisherModel publisher;
    private List<ArtistModel> artists;
    private List<StockModel> stockItems;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public GenreModel getGenre() {
        return genre;
    }

    public void setGenre(GenreModel genre) {
        this.genre = genre;
    }

    public PublisherModel getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherModel publisher) {
        this.publisher = publisher;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistModel> artists) {
        this.artists = artists;
    }

    public List<StockModel> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockModel> stockItems) {
        this.stockItems = stockItems;
    }
}


