package nl.novi.vinylshop.mappers.dto;

import nl.novi.vinylshop.dtos.genre.GenreRequestDTO;
import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.models.GenreModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDTOMapper {

    public GenreResponseDTO mapToDto(GenreModel model) {

        var result = new GenreResponseDTO();
        result.setId(model.getId());
        result.setDescription(model.getDescription());
        result.setName(model.getName());
        return result;
    }


    public List<GenreResponseDTO> mapToDto(List<GenreModel> models) {
        var result = new ArrayList<GenreResponseDTO>();
        for (GenreModel model : models) {
            result.add(mapToDto(model));
        }
        return result;
    }

   
    public GenreModel mapToModel(GenreRequestDTO genreModel) {
        var result = new GenreModel();
        result.setName(genreModel.getName());
        result.setDescription(genreModel.getDescription());
        return result;
    }
}
