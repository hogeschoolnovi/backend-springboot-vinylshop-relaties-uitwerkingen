package nl.novi.vinylshop.mappers.dto;


import nl.novi.vinylshop.dtos.artist.ArtistRequestDTO;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.models.ArtistModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistDTOMapper {
    public ArtistResponseDTO mapToDto(ArtistModel model) {
        var result = new ArtistResponseDTO();
        result.setId(model.getId());
        result.setName(model.getName());
        result.setBiography(model.getBiography());
        return result;
    }

    public List<ArtistResponseDTO> mapToDto(List<ArtistModel> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ArtistModel mapToModel(ArtistRequestDTO requestDTO) {
        var model = new ArtistModel();
        model.setName(requestDTO.getName());
        model.setBiography(requestDTO.getBiography());
        return model;
    }
}