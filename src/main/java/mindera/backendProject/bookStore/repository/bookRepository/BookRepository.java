package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);


}
