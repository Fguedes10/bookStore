package mindera.backendProject.bookStore.repository.bookRepository;

import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE publisher AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
