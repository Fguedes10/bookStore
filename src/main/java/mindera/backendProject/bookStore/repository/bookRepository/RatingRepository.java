package mindera.backendProject.bookStore.repository.bookRepository;

import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
