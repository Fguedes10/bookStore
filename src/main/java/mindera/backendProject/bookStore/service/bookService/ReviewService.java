package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.book.ReviewNotFoundException;

import java.util.List;

public interface ReviewService {
    List<ReviewCreateDto> getAll(int page, int size, String searchTerm);

    ReviewCreateDto getReview(Long reviewId) throws ReviewNotFoundException;

    void delete(Long id) throws ReviewNotFoundException;

}
