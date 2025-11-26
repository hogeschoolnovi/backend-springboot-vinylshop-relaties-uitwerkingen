package nl.novi.vinylshop.mappers.entity;


import nl.novi.vinylshop.entities.StockEntity;
import nl.novi.vinylshop.models.StockModel;
import org.springframework.stereotype.Component;

@Component
public class StockEntityMapper implements EntityMapper<StockModel, StockEntity> {

    @Override
    public StockModel fromEntity(StockEntity entity) {
        if (entity == null) {
            return null;
        }
        var model = new StockModel();
        fromEntityBase(entity,model);
       model.setCondition(entity.getCondition());
       model.setPrice(entity.getPrice());
        return model;
    }

    @Override
    public StockEntity toEntity(StockModel model) {
        if (model == null) {
            return null;
        }
        var entity = new StockEntity();
        toEntityBase(model,entity);
        entity.setCondition(model.getCondition());
        entity.setPrice(model.getPrice());
        return entity;
    }
}
