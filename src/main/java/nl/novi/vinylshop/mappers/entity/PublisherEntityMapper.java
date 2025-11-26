package nl.novi.vinylshop.mappers.entity;

import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.models.PublisherModel;
import org.springframework.stereotype.Component;

@Component
public class PublisherEntityMapper implements EntityMapper<PublisherModel, PublisherEntity> {

    @Override
    public PublisherModel fromEntity(PublisherEntity entity) {
        if (entity == null) {
            return null;
        }
        PublisherModel model = new PublisherModel();
        fromEntityBase(entity,model);
        model.setName(entity.getName());
        model.setAddress(entity.getAddress());
        model.setContactDetails(entity.getContactDetails());
        return model;
    }

    @Override
    public PublisherEntity toEntity(PublisherModel model) {
        if (model == null) {
            return null;
        }
        PublisherEntity entity = new PublisherEntity();
        toEntityBase(model,entity);
        entity.setName(model.getName());
        entity.setAddress(model.getAddress());
        entity.setContactDetails(model.getContactDetails());
        return entity;
    }
}
