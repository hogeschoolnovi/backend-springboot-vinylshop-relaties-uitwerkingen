package nl.novi.vinylshop.controllers;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import nl.novi.vinylshop.dtos.genre.GenreRequestDTO;
import nl.novi.vinylshop.dtos.genre.GenreResponseDTO;
import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.helpers.UrlHelper;
import nl.novi.vinylshop.mappers.dto.GenreDTOMapper;
import nl.novi.vinylshop.models.GenreModel;
import nl.novi.vinylshop.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//GET /genres - Haalt een lijst van alle genres op.
//GET /genres/{id} - Haalt een specifiek genre op basis van ID op.
//POST /genres - Creëert een nieuw genre.
//PUT /genres/{id} - Werkt een bestaand genre bij.
//DELETE /genres/{id} - Verwijdert een genre.


@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreDTOMapper genreDTOMapper;
    private final UrlHelper urlHelper;


    public GenreController(GenreService genreService, GenreDTOMapper genreDTOMapper, UrlHelper urlHelper) {
        this.genreService = genreService;
        this.genreDTOMapper = genreDTOMapper;
        this.urlHelper = urlHelper;

    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() {
        List<GenreModel> genres = genreService.findAllGenres();
        return new ResponseEntity<>(genreDTOMapper.mapToDto(genres), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getGenreById(@PathVariable Long id) throws EntityNotFoundException {
        GenreModel genre = genreService.findGenreById(id);
        return new ResponseEntity<>(genreDTOMapper.mapToDto(genre), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@RequestBody @Valid GenreRequestDTO genreModel) {
        GenreModel newGenre = genreService.createGenre(genreDTOMapper.mapToModel(genreModel));
        GenreResponseDTO genreResponseDTO = genreDTOMapper.mapToDto(newGenre);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(genreResponseDTO.getId())).body(genreResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> updateGenre(@PathVariable Long id, @RequestBody @Valid GenreRequestDTO genreModel) throws EntityNotFoundException {
        GenreModel updatedGenre = genreService.updateGenre(id, genreDTOMapper.mapToModel(genreModel));
        GenreResponseDTO genreResponseDTO = genreDTOMapper.mapToDto(updatedGenre);
        return new ResponseEntity<>(genreResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


