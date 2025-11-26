package nl.novi.vinylshop.controllers;


import nl.novi.vinylshop.dtos.album.AlbumArtistRequestDTO;
import nl.novi.vinylshop.dtos.album.AlbumRequestDTO;
import nl.novi.vinylshop.dtos.album.AlbumResponseDTO;
import nl.novi.vinylshop.dtos.artist.ArtistResponseDTO;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.mappers.dto.AlbumDTOMapper;
import nl.novi.vinylshop.mappers.dto.ArtistDTOMapper;
import nl.novi.vinylshop.models.ArtistModel;
import nl.novi.vinylshop.services.AlbumService;
import nl.novi.vinylshop.services.ArtistService;
import jakarta.persistence.EntityNotFoundException;

import nl.novi.vinylshop.entities.AlbumEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final UrlHelper urlHelper;
    private final AlbumDTOMapper albumDTOMapper;
    private final ArtistDTOMapper artistDTOMapper;


    public AlbumController(AlbumService albumService, ArtistService artistService, UrlHelper urlHelper, AlbumDTOMapper albumDTOMapper, ArtistDTOMapper artistDTOMapper) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.urlHelper = urlHelper;
        this.albumDTOMapper = albumDTOMapper;
        this.artistDTOMapper = artistDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums() {
        var albums = albumService.findAllAlbums();
        var albumDTOs = albumDTOMapper.mapToDtos(albums);
        return new ResponseEntity<>(albumDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable Long id) throws EntityNotFoundException {
        var album = albumService.findAlbumById(id);
        var albumDTO = albumDTOMapper.mapToDto(album);
        return new ResponseEntity<>(albumDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO) {
        var albumModel = albumDTOMapper.mapToModel(albumRequestDTO);
        var newAlbum = albumService.createAlbum(albumModel);
        var albumDTO = albumDTOMapper.mapToDto(newAlbum);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(albumDTO.getId())).body(albumDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> updateAlbum(@PathVariable Long id, @RequestBody  AlbumRequestDTO albumRequestDTO) throws EntityNotFoundException {

        var updatedAlbum = albumService.updateAlbum(id, albumDTOMapper.mapToModel(albumRequestDTO));
        var albumDTO = albumDTOMapper.mapToDto(updatedAlbum);
        return new ResponseEntity<>(albumDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/artists")
    public ResponseEntity<Void> linkArtist(@PathVariable Long id,@RequestBody AlbumArtistRequestDTO albumRequestDTO) {
        albumService.linkArtist(id, albumRequestDTO.getArtistId());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}/artists/{artistId}")
    public ResponseEntity<Void> unlinkArtist(@PathVariable Long id,@PathVariable Long artistId) {
        albumService.unlinkArtist(id, artistId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/artists")
    public ResponseEntity<List<ArtistResponseDTO>> linkArtist(@PathVariable Long id) {
        List<ArtistModel> artists = artistService.getArtistsForAlbum(id);
        return ResponseEntity.ok(artistDTOMapper.mapToDto(artists));
    }
}
