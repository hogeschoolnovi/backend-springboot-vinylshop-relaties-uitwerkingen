package nl.novi.vinylshop.services;


import jakarta.persistence.EntityNotFoundException;
import nl.novi.vinylshop.entities.AlbumEntity;
import nl.novi.vinylshop.entities.ArtistEntity;
import nl.novi.vinylshop.entities.GenreEntity;
import nl.novi.vinylshop.entities.PublisherEntity;
import nl.novi.vinylshop.mappers.entity.AlbumEntityMapper;
import nl.novi.vinylshop.models.AlbumModel;
import nl.novi.vinylshop.repositories.AlbumRepository;
import nl.novi.vinylshop.repositories.ArtistRepository;
import nl.novi.vinylshop.repositories.GenreRepository;
import nl.novi.vinylshop.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final AlbumEntityMapper albumEntityMapper;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;

    public AlbumService(AlbumRepository albumRepository,
                        ArtistRepository artistRepository,
                        AlbumEntityMapper albumEntityMapper,
                        PublisherRepository publisherRepository,
                        GenreRepository genreRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.albumEntityMapper = albumEntityMapper;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<AlbumModel> findAllAlbums() {
        return albumRepository.findAll().stream()
                .map(albumEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlbumModel findAlbumById(Long id) throws EntityNotFoundException {
        AlbumEntity albumEntity = getAlbumEntity(id);
        return albumEntityMapper.fromEntity(albumEntity);
    }

    @Transactional
    public AlbumModel createAlbum(AlbumModel albumModel) {
        AlbumEntity albumEntity = albumEntityMapper.toEntity(albumModel);
        albumEntity = albumRepository.save(albumEntity);
        return albumEntityMapper.fromEntity(albumEntity);
    }

    @Transactional
    public AlbumModel updateAlbum(Long id, AlbumModel albumModel) throws EntityNotFoundException {
        AlbumEntity existingAlbumEntity = getAlbumEntity(id);

        existingAlbumEntity.setTitle(albumModel.getTitle());
        existingAlbumEntity.setReleaseYear(albumModel.getReleaseYear());
        existingAlbumEntity.setPublisher(getPublisherEntity(albumModel.getPublisher().getId()));
        existingAlbumEntity.setGenre(getGenreEntity(albumModel.getGenre().getId()));
        existingAlbumEntity = albumRepository.save(existingAlbumEntity);
        return albumEntityMapper.fromEntity(existingAlbumEntity);
    }

    private PublisherEntity getPublisherEntity(long publisherId) {
        return publisherRepository.findById(publisherId).orElseThrow(() -> new EntityNotFoundException("publisher " + publisherId + " not found"));
    }

    private GenreEntity getGenreEntity(long genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("genre " + genreId + " not found"));
    }

    private AlbumEntity getAlbumEntity(Long id) {
        AlbumEntity existingAlbumEntity = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album " + id + " not found"));
        return existingAlbumEntity;
    }

    @Transactional
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    @Transactional
    public void linkArtist(Long albumId, Long artistId) {
        AlbumEntity existingAlbumEntity = getAlbumEntity(albumId);
        ArtistEntity existingArtistEntity = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist " + artistId + " not found"));
        existingArtistEntity.getAlbums().add(existingAlbumEntity);
        existingAlbumEntity.getArtists().add(existingArtistEntity);
        albumRepository.save(existingAlbumEntity);
    }

    @Transactional
    public void unlinkArtist(Long albumId, Long artistId) {
        AlbumEntity existingAlbumEntity = getAlbumEntity(albumId);
        ArtistEntity existingArtistEntity = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist " + artistId + " not found"));
        existingArtistEntity.getAlbums().remove(existingAlbumEntity);
        existingAlbumEntity.getArtists().remove(existingArtistEntity);
        albumRepository.save(existingAlbumEntity);
    }

    @Transactional(readOnly = true)
    public List<AlbumModel> getAlbumsWithStock() {
        return albumRepository.findAlbumsWithStock().stream()
                .map(albumEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }
}

