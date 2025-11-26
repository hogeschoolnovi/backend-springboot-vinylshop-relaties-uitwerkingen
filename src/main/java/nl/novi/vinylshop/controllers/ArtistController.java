package nl.novi.vinylshop.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.artist.ArtistRequestDTO;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.mappers.dto.ArtistDTOMapper;
import nl.novi.vinylshop.services.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistDTOMapper artistDTOMapper;
    private final UrlHelper urlHelper;

    public ArtistController(ArtistService artistService, ArtistDTOMapper artistDTOMapper, UrlHelper urlHelper ) {
        this.artistService = artistService;
        this.artistDTOMapper = artistDTOMapper;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> getAllArtists() {
        var artists = artistService.findAllArtists();
        var artistDTOs = artistDTOMapper.mapToDto(artists);
        return new ResponseEntity<>(artistDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) throws EntityNotFoundException {
        var artist = artistService.findArtistById(id);
        var artistDTO = artistDTOMapper.mapToDto(artist);
        return new ResponseEntity<>(artistDTO, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ArtistResponseDTO> createArtist(@RequestBody @Valid ArtistRequestDTO artistRequestDTO) {
        var artistModel = artistDTOMapper.mapToModel(artistRequestDTO);
        var newArtist = artistService.createArtist(artistModel);
        var artistDTO = artistDTOMapper.mapToDto(newArtist);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(artistDTO.getId())).body(artistDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> updateArtist(@PathVariable Long id, @RequestBody @Valid ArtistRequestDTO artistRequestDTO) throws EntityNotFoundException {
        var updatedArtist = artistService.updateArtist(id, artistDTOMapper.mapToModel(artistRequestDTO));
        var artistDTO = artistDTOMapper.mapToDto(updatedArtist);
        return new ResponseEntity<>(artistDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
