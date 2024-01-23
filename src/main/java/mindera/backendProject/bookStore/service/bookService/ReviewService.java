package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.ReviewCreateDto;

import java.util.List;

public interface ReviewService {
    List<ReviewCreateDto> getAll();

    ReviewCreateDto getReview(Long reviewId);

    ReviewCreateDto add(ReviewCreateDto author);

    void delete(Long id);
}
