package nl.novi.vinylshop.mappers.entity;

import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.models.ArtistModel;
import org.springframework.stereotype.Component;

@Component
public class ArtistEntityMapper implements EntityMapper<ArtistModel, ArtistEntity> {
    @Override
    public ArtistModel fromEntity(ArtistEntity entity) {
        if (entity == null) {
            return null;
        }
        ArtistModel model = new ArtistModel();
        fromEntityBase(entity,model);
        model.setName(entity.getName());
        model.setBiography(entity.getBiography());
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
