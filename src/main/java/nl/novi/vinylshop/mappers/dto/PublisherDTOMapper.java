package nl.novi.vinylshop.mappers.dto;

import nl.novi.vinylshop.dtos.publisher.PublisherRequestDTO;
import nl.novi.vinylshop.dtos.publisher.PublisherResponseDTO;
import nl.novi.vinylshop.models.PublisherModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublisherDTOMapper {

    public PublisherResponseDTO mapToDto(PublisherModel publisher) {
        PublisherResponseDTO dto = new PublisherResponseDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setAddress(publisher.getAddress());
        dto.setContactDetails(publisher.getContactDetails());
        return dto;
    }

    public List<PublisherResponseDTO> mapToDto(List<PublisherModel> publishers) {
        List<PublisherResponseDTO> result = new ArrayList<>();
        for (PublisherModel publisher : publishers) {
            result.add(mapToDto(publisher));
        }
        return result;
    }

    public PublisherModel mapToModel(PublisherRequestDTO dto) {
        PublisherModel publisher = new PublisherModel();
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setContactDetails(dto.getContactDetails());
        return publisher;
    }
}
