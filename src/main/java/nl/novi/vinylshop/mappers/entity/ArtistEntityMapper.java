package nl.novi.vinylshop.mappers.entity;

import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.models.ArtistModel;
import org.springframework.stereotype.Component;

@Component
public class ArtistEntityMapper implements EntityMapper<ArtistModel, ArtistEntity> {

    private final AlbumEntityMapper albumEntityMapper;

    public ArtistEntityMapper(AlbumEntityMapper albumEntityMapper) {
        this.albumEntityMapper = albumEntityMapper;
    }

    @Override
    public ArtistModel fromEntity(ArtistEntity entity) {
        if (entity == null) {
            return null;
        }
        ArtistModel model = new ArtistModel();
        fromEntityBase(entity,model);
        model.setName(entity.getName());
        model.setBiography(entity.getBiography());
//        TODO: check of dit geen recursie veroorzaakt
        if (entity.getAlbums() != null) {
            model.setAlbums(entity.getAlbums().stream().map(
                    albumEntityMapper::fromEntity
            ).toList());
        }
        return model;
    }

    @Override
    public ArtistEntity toEntity(ArtistModel model) {
        if (model == null) {
            return null;
        }
        ArtistEntity entity = new ArtistEntity();
        toEntityBase(model,entity);

        entity.setName(model.getName());
        entity.setBiography(model.getBiography());
        return entity;
    }
}
