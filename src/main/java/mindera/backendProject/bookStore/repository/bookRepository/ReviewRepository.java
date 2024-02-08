package mindera.backendProject.bookStore.repository.bookRepository;

import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {

    Page<Review> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE review AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
