package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Optional<Object> findByName(String name);
}
