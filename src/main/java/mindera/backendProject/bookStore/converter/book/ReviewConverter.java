package mindera.backendProject.bookStore.converter.book;

import mindera.backendProject.bookStore.dto.book.ReviewAddNewDto;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.dto.book.ReviewGetDto;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Review;

import java.time.LocalDate;

public class ReviewConverter {

    public static Review fromCreateDtoToModel(ReviewCreateDto reviewCreateDto) {
        return Review.builder()
                .comment(reviewCreateDto.comment())
                .build();
    }

    public static Review fromReviewAddNewDtoToModel(ReviewAddNewDto reviewAddNewDto, Book book) {
        return Review.builder()
                .book(book)
                .comment(reviewAddNewDto.comment())
                .commentDate(LocalDate.now())
                .build();
    }



    public static ReviewCreateDto fromModelToReviewCreateDto(Review review) {
        return new ReviewCreateDto(
                review.getComment()
        );
    }

    public static ReviewGetDto fromModelToReviewGetDto(Review review) {
        return new ReviewGetDto(
                review.getBook().getId(),
                review.getBook().getTitle(),
                review.getComment(),
                review.getCommentDate()
        );
    }

}

