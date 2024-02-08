package mindera.backendProject.bookStore.repository.bookRepository;

import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByName(String name);

    List<Translation> findAllByIdIn(List<Long> translationIds);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE translation AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
