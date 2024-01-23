package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
