package nl.novi.vinylshop.mappers.dto;


import nl.novi.vinylshop.dtos.album.AlbumExtendedResponseDTO;
import nl.novi.vinylshop.models.AlbumModel;
import org.springframework.stereotype.Component;


@Component
public class AlbumExtendedDTOMapper extends AlbumDTOMapper {

    private final StockDTOMapper stockDTOMapper;

    public AlbumExtendedDTOMapper(PublisherDTOMapper publisherDtoMapper , StockDTOMapper stockDTOMapper,GenreDTOMapper genreDTOMapper) {
        super(publisherDtoMapper,genreDTOMapper);
        this.stockDTOMapper = stockDTOMapper;
    }

    public AlbumExtendedResponseDTO mapToDto(AlbumModel model) {
        var result = super.mapToDto(model, new AlbumExtendedResponseDTO());
        result.setStock(stockDTOMapper.mapToDtos(model.getStockItems()));
        return result;
    }
}
