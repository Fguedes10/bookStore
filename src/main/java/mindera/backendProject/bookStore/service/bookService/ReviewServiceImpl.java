package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.converter.book.ReviewConverter;
import mindera.backendProject.bookStore.dto.book.ReviewAddNewDto;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.dto.book.ReviewGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.book.ReviewNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Review;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.bookRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class ReviewServiceImpl implements ReviewService {


    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public List<ReviewCreateDto> getAll(int page, int size, String searchTerm) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, searchTerm);
        Page<Review> reviews = reviewRepository.findAll(pageRequest);
        return reviews.stream().filter(review -> !review.getComment().equals(NO_REVIEW)).map(ReviewConverter::fromModelToReviewCreateDto).toList();
    }


    @Override
    public ReviewCreateDto getReview(Long reviewId) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new ReviewNotFoundException(REVIEW_WITH_ID + reviewId + DOESNT_EXIST);
        }
        return ReviewConverter.fromModelToReviewCreateDto(reviewOptional.get());
    }

    public ReviewGetDto addReview(ReviewAddNewDto reviewAddNewDto) throws BookNotFoundException {
        Optional<Book> checkIfBookExists = bookRepository.findById(reviewAddNewDto.bookId());
        if(checkIfBookExists.isEmpty()){
            throw new BookNotFoundException(BOOK_WITH_ID  + reviewAddNewDto.bookId() + DOESNT_EXIST);
        }
        Book checkFirstReview = checkIfBookExists.get();
        if(checkFirstReview.getReview().getFirst().getComment().equals(NO_REVIEW)){
            checkIfBookExists.get().getReview().removeFirst();
        }
        Review reviewToSave = ReviewConverter.fromReviewAddNewDtoToModel(reviewAddNewDto, checkIfBookExists.get());
        reviewRepository.save(reviewToSave);
        return ReviewConverter.fromModelToReviewGetDto(reviewToSave);
    }

    public void addFirstReview(Book book){
        Review firstReview = new Review();
        firstReview.setBook(book);
        firstReview.setCommentDate(LocalDate.now());
        firstReview.setComment(NO_REVIEW);
        reviewRepository.save(firstReview);
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
