package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Translation> findByName(String name);
}
