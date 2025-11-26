package nl.novi.vinylshop.controllers;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.publisher.PublisherRequestDTO;
import nl.novi.vinylshop.dtos.publisher.PublisherResponseDTO;
import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.mappers.dto.PublisherDTOMapper;
import nl.novi.vinylshop.models.PublisherModel;
import nl.novi.vinylshop.services.PublisherService;
import nl.novi.vinylshop.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//GET /publishers - Haalt een lijst van alle publishers op.
//GET /publishers/{id} - Haalt een specifiek publisher op basis van ID op.
//POST /publishers - Creëert een nieuw publisher.
//PUT /publishers/{id} - Werkt een bestaand publisher bij.
//DELETE /publishers/{id} - Verwijdert een publisher.


@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    private final PublisherDTOMapper publisherDTOMapper;
    private final UrlHelper urlHelper;

    public PublisherController(PublisherService publisherService, PublisherDTOMapper publisherDTOMapper, UrlHelper urlHelper) {
        this.publisherService = publisherService;
        this.publisherDTOMapper = publisherDTOMapper;
        this.urlHelper = urlHelper;

    }
    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> getAllPublishers() {
        List<PublisherModel> publishers = publisherService.findAllPublishers();
        return new ResponseEntity<>(publisherDTOMapper.mapToDto(publishers), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> getPublisherById(@PathVariable Long id) throws EntityNotFoundException {
        PublisherModel publisher = publisherService.findPublisherById(id);
        return new ResponseEntity<>(publisherDTOMapper.mapToDto(publisher), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> createPublisher(@RequestBody  @Valid PublisherRequestDTO publisherRequestDTO) {
        PublisherModel newPublisher = publisherService.createPublisher(publisherDTOMapper.mapToModel(publisherRequestDTO));
        PublisherResponseDTO publisherResponseDTO = publisherDTOMapper.mapToDto(newPublisher);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(publisherResponseDTO.getId())).body(publisherResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> updatePublisher(@PathVariable Long id, @RequestBody  @Valid PublisherRequestDTO publisherRequestDTO) throws EntityNotFoundException {
        PublisherModel updatedPublisher = publisherService.updatePublisher(id, publisherDTOMapper.mapToModel(publisherRequestDTO));
        return new ResponseEntity<>(publisherDTOMapper.mapToDto(updatedPublisher), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


