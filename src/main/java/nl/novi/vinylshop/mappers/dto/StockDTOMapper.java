package nl.novi.vinylshop.mappers.dto;

import nl.novi.vinylshop.dtos.stock.StockRequestDTO;
import nl.novi.vinylshop.dtos.stock.StockResponseDTO;
import nl.novi.vinylshop.models.StockModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockDTOMapper {

    public StockResponseDTO mapToDto(StockModel model) {
        var result = new StockResponseDTO();
        result.setId(model.getId());
        result.setCondition(model.getCondition());
        result.setPrice(model.getPrice());
        return result;
    }

    public List<StockResponseDTO> mapToDtos(List<StockModel> models) {
        if(models == null) { return new ArrayList<StockResponseDTO>();}
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public StockModel mapToModel(StockRequestDTO requestDTO) {
        var model = new StockModel();
        model.setCondition(requestDTO.getCondition());
        model.setPrice(requestDTO.getPrice());
        return model;
    }
}
