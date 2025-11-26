package nl.novi.vinylshop.mappers.entity;



import nl.novi.vinylshop.entities.BaseEntity;
import nl.novi.vinylshop.models.BaseModel;

import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<M extends BaseModel, E extends BaseEntity> {
    M fromEntity(E entity);

    E toEntity(M dto);


    default void fromEntityBase(E entity, M model) {
        if (entity == null || model == null) {
            return;
        }
        model.setId(entity.getId());
        model.setCreateDate(entity.getCreateDate());
        model.setEditDate(entity.getEditDate());
    }

    default void toEntityBase(M model, E entity ) {
        if (model == null || entity == null) {
            return;
        }
        entity.setId(model.getId());

        if(model.getCreateDate() != null) {
            entity.setCreateDate(model.getCreateDate());
        }

        if(model.getEditDate() != null) {
            entity.setEditDate(model.getEditDate());
        }
    }

    default List<M> fromEntities(List<E> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    default List<E> toEntities(List<M> models) {
        if (models == null) {
            return null;
        }
        return models.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}