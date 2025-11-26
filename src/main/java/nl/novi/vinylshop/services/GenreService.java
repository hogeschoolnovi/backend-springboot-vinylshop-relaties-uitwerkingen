package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.mappers.entity.GenreEntityMapper;
import nl.novi.vinylshop.models.GenreModel;
import nl.novi.vinylshop.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreEntityMapper genreEntityMapper;


    public GenreService(GenreRepository genreRepository, GenreEntityMapper genreEntityMapper) {
        this.genreRepository = genreRepository;
        this.genreEntityMapper = genreEntityMapper;
    }



    public List<GenreModel> findAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public GenreModel findGenreById(Long id) throws EntityNotFoundException {
        GenreEntity genreEntity = getGenreEntity(id);
        return genreEntityMapper.fromEntity(genreEntity);
    }

    public GenreModel createGenre(GenreModel genreModel) {
        GenreEntity genreEntity = genreEntityMapper.toEntity(genreModel);
        genreEntity = genreRepository.save(genreEntity);
        return genreEntityMapper.fromEntity(genreEntity);
    }

    public GenreModel updateGenre(Long id, GenreModel genreModel) throws EntityNotFoundException {
        GenreEntity existingGenreEntity = getGenreEntity(id);

        existingGenreEntity.setName(genreModel.getName());
        existingGenreEntity.setDescription(genreModel.getDescription());

        existingGenreEntity = genreRepository.save(existingGenreEntity);
        return genreEntityMapper.fromEntity(existingGenreEntity);
    }

    private GenreEntity getGenreEntity(Long id) {
        GenreEntity existingGenreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre " + id +" not found"));
        return existingGenreEntity;
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    private GenreEntity getGenreById(Long id){
        Optional<GenreEntity> genreEntityOptional = genreRepository.findById(id);

//        Een if-statement waar je expliciet de Optional.isPresent() of Optional.isEmpty() checkt, is één variant om met de optional om te gaan.
        if(genreEntityOptional.isPresent()){
            return genreEntityOptional.get();
        } else {
            return null;
        }
    }
}
