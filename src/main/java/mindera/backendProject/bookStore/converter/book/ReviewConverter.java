package mindera.backendProject.bookStore.converter.book;

import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.model.Review;

public class ReviewConverter {

    public static Review fromCreateDtoToModel(ReviewCreateDto reviewCreateDto) {
        return Review.builder()
                .comment(reviewCreateDto.comment())
                .build();
    }


    public static ReviewCreateDto fromModelToReviewCreateDto(Review review) {
        return new ReviewCreateDto(
                review.getComment()
        );
    }
}
