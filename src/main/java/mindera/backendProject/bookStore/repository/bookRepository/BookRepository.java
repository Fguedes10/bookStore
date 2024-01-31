package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsbn(Long isbn);

    List<Book> findBooksByYearRelease(int releaseYear);

    @Query("SELECT b FROM Book b JOIN b.translation t WHERE t.id = :translationId")
    List<Book> findBooksByTranslation(@Param("translationId") Long translationId);

}
