package nl.novi.vinylshop.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.mappers.entity.ArtistEntityMapper;
import nl.novi.vinylshop.models.ArtistModel;
import nl.novi.vinylshop.repositories.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistEntityMapper artistEntityMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistEntityMapper artistEntityMapper) {
        this.artistRepository = artistRepository;
        this.artistEntityMapper = artistEntityMapper;
    }

    @Transactional(readOnly = true)
    public List<ArtistModel> findAllArtists() {
        return artistRepository.findAll().stream()
                .map(artistEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArtistModel findArtistById(Long id) throws EntityNotFoundException {
        ArtistEntity artistEntity = getArtistEntity(id);
        return artistEntityMapper.fromEntity(artistEntity);
    }

    private ArtistEntity getArtistEntity(Long id) {
        ArtistEntity artistEntity = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist " + id + " not found"));
        return artistEntity;
    }

    @Transactional
    public ArtistModel createArtist(ArtistModel artistModel) {
        ArtistEntity artistEntity = artistEntityMapper.toEntity(artistModel);
        artistEntity = artistRepository.save(artistEntity);
        return artistEntityMapper.fromEntity(artistEntity);
    }

    @Transactional
    public ArtistModel updateArtist(Long id, ArtistModel artistModel) throws EntityNotFoundException {
        ArtistEntity existingArtistEntity = getArtistEntity(id);

        existingArtistEntity.setName(artistModel.getName());
        existingArtistEntity.setBiography(artistModel.getBiography());

        existingArtistEntity = artistRepository.save(existingArtistEntity);
        return artistEntityMapper.fromEntity(existingArtistEntity);
    }

    @Transactional
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    public List<ArtistModel> getArtistsForAlbum(Long albumId) {
        return artistRepository.findArtistsByAlbumId(albumId).stream()
                .map(artistEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
