package mindera.backendProject.bookStore.repository.bookRepository;

import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsbn(Long isbn);


    List<Book> findBooksByYearRelease(int releaseYear);

    @Query("SELECT b FROM Book b JOIN b.translation t WHERE t.id = :translationId")
    List<Book> findBooksByTranslation(@Param("translationId") Long translationId);


    List<Book> findAllByIdIn(List<Long> bookIds);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE book AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}
