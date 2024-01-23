package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.RatingCreateDto;

public interface RatingService {

    RatingCreateDto getRating(Long ratingId);
    RatingCreateDto getRatingByValue(int rantingValue);
}
