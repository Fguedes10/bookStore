package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.ReviewConverter;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.book.ReviewNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Review;
import mindera.backendProject.bookStore.repository.bookRepository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.DOESNT_EXIST;
import static mindera.backendProject.bookStore.util.Messages.REVIEW_WITH_ID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewCreateDto> getAll() {
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream().map(ReviewConverter::fromModelToReviewCreateDto).toList();
    }

    @Override
    public ReviewCreateDto getReview(Long reviewId) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new ReviewNotFoundException(REVIEW_WITH_ID + reviewId + DOESNT_EXIST);
        }
        return ReviewConverter.fromModelToReviewCreateDto(reviewOptional.get());
    }

    public void addFirstReview(Book book) {
        Review noReviewsYet = new Review("This book doesn't have any reviews");
        noReviewsYet.setBook(book);
        noReviewsYet.setCommentDate(LocalDate.now());

        if(book.getReview() == null){
            book.setReview(new ArrayList<>());
        }
        book.addReview(noReviewsYet);
        reviewRepository.save(noReviewsYet);
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new ReviewNotFoundException(REVIEW_WITH_ID + reviewId + DOESNT_EXIST);
        }
        reviewRepository.delete(reviewOptional.get());
    }
}
