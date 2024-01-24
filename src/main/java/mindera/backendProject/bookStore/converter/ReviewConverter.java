package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.model.Review;

public class ReviewConverter {

    public static Review fromCreateDtoToModel(ReviewCreateDto reviewCreateDto) {
        return Review.builder()
                .isbn(reviewCreateDto.isbn())
                .anonymous(reviewCreateDto.anonymous())
                .comment(reviewCreateDto.comment())
                .commentDate(reviewCreateDto.commentDate())
                .build();
    }


    public static ReviewCreateDto fromModelToReviewCreateDto(Review review) {
        return new ReviewCreateDto(
                review.getIsbn(),
                review.isAnonymous(),
                review.getComment(),
                review.getCommentDate()
        );
    }
}
