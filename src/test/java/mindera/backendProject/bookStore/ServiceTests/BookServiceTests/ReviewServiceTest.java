package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.ReviewConverter;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.book.ReviewNotFoundException;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Review;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import mindera.backendProject.bookStore.repository.bookRepository.ReviewRepository;
import mindera.backendProject.bookStore.service.bookService.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
    static MockedStatic<ReviewConverter> mockedReviewConverter = Mockito.mockStatic(ReviewConverter.class);

    @MockBean
    private ReviewRepository reviewRepositoryMock;
    @MockBean
    private BookRepository bookRepositoryMock;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setUp() {
        reviewService = new ReviewServiceImpl(reviewRepositoryMock, bookRepositoryMock);
    }

    @Test
    @DisplayName("Get all reviews and check repository")
    void testGetAll() {

        // GIVEN
        int page = 0;
        int size = 10;
        String searchTerm = "title";

        Review review1 = Review.builder().id(1L).comment("Great Book").commentDate(LocalDate.now()).book(new Book()).build();
        Review review2 = Review.builder().id(2L).comment("Excellent read").commentDate(LocalDate.now()).book(new Book()).build();

        List<Review> reviewList = List.of(review1, review2);

        Page<Review> mockedPage = new PageImpl<>(reviewList);
        when(reviewRepositoryMock.findAll(any(PageRequest.class))).thenReturn(mockedPage);
        mockedReviewConverter.when(() -> ReviewConverter.fromModelToReviewCreateDto(review1)).thenReturn(new ReviewCreateDto(review1.getComment()));
        mockedReviewConverter.when(() -> ReviewConverter.fromModelToReviewCreateDto(review1)).thenReturn(new ReviewCreateDto(review2.getComment()));
        when(ReviewConverter.fromModelToReviewCreateDto(any())).thenReturn(new ReviewCreateDto(review1.getComment()));
        when(ReviewConverter.fromModelToReviewCreateDto(any())).thenReturn(new ReviewCreateDto(review2.getComment()));

        // WHEN
        List<ReviewCreateDto> result = reviewService.getAll(page, size, searchTerm);

        // THEN
        assertNotNull(result);
        assertEquals(reviewList.size(), result.size());
        verify(reviewRepositoryMock).findAll(PageRequest.of(page, size, Sort.Direction.DESC, searchTerm));
        mockedReviewConverter.verify(() -> ReviewConverter.fromModelToReviewCreateDto(review1));
        mockedReviewConverter.verify(() -> ReviewConverter.fromModelToReviewCreateDto(review2));
    }

    @Test
    @DisplayName("Get review by id and check repository")
    void testGetReview() throws ReviewNotFoundException {

        // GIVEN
        Long reviewId = 1L;
        Review review = Review.builder().id(reviewId).comment("Great Book").commentDate(LocalDate.now()).book(new Book()).build();

        when(reviewRepositoryMock.findById(reviewId)).thenReturn(Optional.of(review));
        mockedReviewConverter.when(() -> ReviewConverter.fromModelToReviewCreateDto(review)).thenReturn(new ReviewCreateDto(review.getComment()));

        // WHEN
        ReviewCreateDto resultDto = reviewService.getReview(reviewId);

        // THEN
        assertNotNull(resultDto);
        assertEquals(review.getComment(), resultDto.comment());
        verify(reviewRepositoryMock).findById(reviewId);
        mockedReviewConverter.verify(() -> ReviewConverter.fromModelToReviewCreateDto(review));
    }

    @Test
    @DisplayName("Add first review and check repository")
    void testAddFirstReview() {

        // GIVEN
        Book book = new Book();

        // WHEN
        reviewService.addFirstReview(book);

        // THEN
        ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);

        verify(reviewRepositoryMock).save(reviewCaptor.capture());

        Review savedReview = reviewCaptor.getValue();
        assertNotNull(savedReview);
        assertEquals(book, savedReview.getBook());
        assertEquals(LocalDate.now(), savedReview.getCommentDate());
        assertEquals("No reviews for this book yet", savedReview.getComment());
    }


}



