package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.model.Review;

public class ReviewConverter {

    public static Review fromCreateDtoToModel(ReviewCreateDto reviewCreateDto) {
        return Review.builder()
                .comment(reviewCreateDto.comment())
                .commentDate(reviewCreateDto.commentDate())
                .build();
    }


    public static ReviewCreateDto fromModelToReviewCreateDto(Review review) {
        return new ReviewCreateDto(
                review.getComment(),
                review.getCommentDate()
        );
    }
}

