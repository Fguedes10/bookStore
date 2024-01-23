package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
