package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.ReviewConverter;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.BookNotFoundException;
import mindera.backendProject.bookStore.exception.ReviewNotFoundException;
import mindera.backendProject.bookStore.model.Review;
import mindera.backendProject.bookStore.repository.bookRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Autowired
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
    public void add(ReviewCreateDto review) {
       reviewRepository.save(ReviewConverter.fromCreateDtoToModel(review));
    }

    @Override
    public void delete(Long reviewId) throws ReviewNotFoundException {
        reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review with id " + reviewId + "does not exist"));
        reviewRepository.deleteById(reviewId);
    }
}
