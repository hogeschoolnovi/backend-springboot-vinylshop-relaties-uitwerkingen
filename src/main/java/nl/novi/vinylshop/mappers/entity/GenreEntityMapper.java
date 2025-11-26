package nl.novi.vinylshop.mappers.entity;

import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.models.GenreModel;
import org.springframework.stereotype.Component;

@Component
public class GenreEntityMapper implements EntityMapper<GenreModel, GenreEntity> {

    @Override
    public GenreModel fromEntity(GenreEntity entity) {
        if (entity == null) {
            return null;
        }
        GenreModel model = new GenreModel();
        fromEntityBase(entity,model);
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        return model;
    }

    @Override
    public GenreEntity toEntity(GenreModel model) {
        if (model == null) {
            return null;
        }
        GenreEntity entity = new GenreEntity();
        toEntityBase(model, entity);
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        return entity;
//        return updateGenre(model, entity);
    }

//    public GenreEntity updateGenre(GenreModel model, GenreEntity entity) {
//        toEntityBase(model, entity);
//        entity.setName(model.getName());
//        entity.setDescription(model.getDescription());
//        return entity;
//    }
}
