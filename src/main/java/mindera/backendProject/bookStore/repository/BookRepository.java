package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
