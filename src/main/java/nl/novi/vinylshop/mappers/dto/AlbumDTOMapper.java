package nl.novi.vinylshop.mappers.dto;


import nl.novi.vinylshop.dtos.album.AlbumRequestDTO;
import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.models.AlbumModel;
import nl.novi.vinylshop.models.GenreModel;
import nl.novi.vinylshop.models.PublisherModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumDTOMapper {

    private final PublisherDTOMapper publisherDtoMapper;
    private final GenreDTOMapper genreDTOMapper;

    public AlbumDTOMapper(PublisherDTOMapper publisherDtoMapper, GenreDTOMapper genreDTOMapper) {
        this.publisherDtoMapper = publisherDtoMapper;
        this.genreDTOMapper = genreDTOMapper;
    }

    public AlbumResponseDTO mapToDto(AlbumModel model) {
        return mapToDto(model, new AlbumResponseDTO());
    }

    public <D extends AlbumResponseDTO> D mapToDto(AlbumModel model, D target) {
        target.setId(model.getId());
        target.setTitle(model.getTitle());
        target.setPublishedYear(model.getReleaseYear());
        target.setGenre(genreDTOMapper.mapToDto(model.getGenre()));
        target.setPublisher(publisherDtoMapper.mapToDto(model.getPublisher()));
        return target;
    }

    public List<AlbumResponseDTO> mapToDtos(List<AlbumModel> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public AlbumModel mapToModel(AlbumRequestDTO requestDTO) {
        var model = new AlbumModel();
        model.setTitle(requestDTO.getTitle());
        model.setReleaseYear(requestDTO.getPublishedYear());
//        Deze twee voeg je in de service toe:
//        model.setGenre(new GenreModel());
//        model.setPublisher(new PublisherModel());
        return model;
    }
}
