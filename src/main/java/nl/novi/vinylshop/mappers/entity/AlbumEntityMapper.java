package nl.novi.vinylshop.mappers.entity;


import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.models.AlbumModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AlbumEntityMapper implements EntityMapper<AlbumModel, AlbumEntity> {
    private final PublisherEntityMapper publisherEntityMapper;
    private final ArtistEntityMapper artistEntityMapper;
    private final StockEntityMapper stockEntityMapper;
    private final GenreEntityMapper genreEntityMapper;

    public AlbumEntityMapper(PublisherEntityMapper publisherEntityMapper, ArtistEntityMapper artistEntityMapper, StockEntityMapper stockEntityMapper, GenreEntityMapper genreEntityMapper) {
        this.publisherEntityMapper = publisherEntityMapper;
        this.artistEntityMapper = artistEntityMapper;
        this.stockEntityMapper = stockEntityMapper;
        this.genreEntityMapper = genreEntityMapper;
    }

    @Override
    public AlbumModel fromEntity(AlbumEntity entity) {
        if (entity == null) {
            return null;
        }
        AlbumModel model = new AlbumModel();
        fromEntityBase(entity,model);
        model.setTitle(entity.getTitle());
        model.setReleaseYear(entity.getReleaseYear());
        model.setGenre(genreEntityMapper.fromEntity(entity.getGenre()));
        model.setPublisher(publisherEntityMapper.fromEntity(entity.getPublisher()));
        if(entity.getArtists() != null) {
            model.setArtists(entity.getArtists().stream().map(artistEntityMapper::fromEntity).toList());
        }
        if(entity.getStockItems() != null) {
            model.setStockItems(entity.getStockItems().stream().map(stockEntityMapper::fromEntity).toList());
        }
        return model;
    }

    @Override
    public AlbumEntity toEntity(AlbumModel model) {
        if (model == null) {
            return null;
        }
        AlbumEntity entity = new AlbumEntity();
        toEntityBase(model,entity);
        entity.setTitle(model.getTitle());
        entity.setReleaseYear(model.getReleaseYear());
        entity.setGenre(genreEntityMapper.toEntity(model.getGenre()));
        entity.setPublisher(publisherEntityMapper.toEntity(model.getPublisher()));

        if (model.getArtists() != null) {
            entity.setArtists(model.getArtists().stream().map(artistEntityMapper::toEntity).collect(Collectors.toSet()));
        }
        if (model.getStockItems() != null) {
            entity.setStockItems(model.getStockItems().stream().map(stockEntityMapper::toEntity).toList());
        }
        return entity;
    }
}


