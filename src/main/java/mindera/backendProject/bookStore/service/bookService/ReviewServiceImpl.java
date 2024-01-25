package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.ReviewConverter;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.ReviewNotFoundException;
import mindera.backendProject.bookStore.model.Review;
import mindera.backendProject.bookStore.repository.bookRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository){
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
        if(reviewOptional.isEmpty()){
            throw new ReviewNotFoundException("Review with id" + reviewId + "does not exist");
        }
        return ReviewConverter.fromModelToReviewCreateDto(reviewOptional.get());
    }

    @Override
    public ReviewCreateDto add(ReviewCreateDto review) {
        return null;
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if(reviewOptional.isEmpty()){
            throw new ReviewNotFoundException("Review with id " + reviewId + "does not exist");
        }
        reviewRepository.delete(reviewOptional.get());
    }
}
