package mindera.backendProject.bookStore.repository.bookRepository;

import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

    List<Genre> findAllByIdIn(List<Long> genreIds);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE genre AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
