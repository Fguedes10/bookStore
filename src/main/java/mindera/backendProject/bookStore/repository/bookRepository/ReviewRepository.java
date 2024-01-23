package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
