package nl.novi.vinylshop.repositories;

import nl.novi.vinylshop.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    @Query("SELECT DISTINCT a FROM ArtistEntity a JOIN a.albums al WHERE al.id = :albumId")
    List<ArtistEntity> findArtistsByAlbumId(@Param("albumId") Long albumId);
}
