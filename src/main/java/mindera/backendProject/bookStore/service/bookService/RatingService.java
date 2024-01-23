package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dtos.books.AuthorCreateDto;
import mindera.backendProject.bookStore.dtos.books.RatingCreateDto;

public interface RatingService {

    RatingCreateDto getRating(Long ratingId);
    RatingCreateDto getRatingByValue(int rantingValue);
}
