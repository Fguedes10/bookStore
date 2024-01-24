package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Object> findByName(String name);
}
