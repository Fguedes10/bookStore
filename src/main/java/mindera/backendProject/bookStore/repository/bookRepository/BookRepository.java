package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Object> findByTittle(String title);

    Optional<Object> findByIsbn(String isbn);


}
