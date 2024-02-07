package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mindera.backendProject.bookStore.dto.book.ReviewAddNewDto;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.dto.book.ReviewGetDto;
import mindera.backendProject.bookStore.exception.book.BookNotFoundException;
import mindera.backendProject.bookStore.exception.book.ReviewNotFoundException;
import mindera.backendProject.bookStore.model.Review;
import mindera.backendProject.bookStore.service.bookService.ReviewServiceImpl;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = REVIEW_TAG_NAME, description = REVIEW_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;


    @Operation(
            summary = GET_ALL_EXIST_REVIEWS,
            description = GET_ALL_EXIST_REVIEWS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = GET_ALL_REVIEWS)})
    @GetMapping("/search")
    public ResponseEntity<List<ReviewCreateDto>> getReviews(@RequestParam (defaultValue = "0", required = false) int page,
                                                            @RequestParam (defaultValue = "10", required = false) int size,
                                                            @RequestParam(defaultValue = "commentDate") String searchTerm) {
        return ResponseEntity.ok(reviewService.getAll(page, size, searchTerm));
    }


    @Operation(
            summary = GET_REVIEW_BY_ID,
            description = GET_REVIEW_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = REVIEW_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = REVIEW_NOT_FOUND)})
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewCreateDto> getReview(@PathVariable("reviewId")@Parameter(name = "Review Id", description = "Review id", example = "1") Long reviewId) throws ReviewNotFoundException{
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }



    @Operation(
            summary = ADD_NEW_REVIEW,
            description = ADD_NEW_REVIEW
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = REVIEW_CREATED),
            @ApiResponse(responseCode = NOT_FOUND, description = BOOK_NOT_FOUND)})
    @PostMapping("/")
    public ResponseEntity<ReviewGetDto> addnewReview(@RequestBody ReviewAddNewDto review) throws BookNotFoundException {
        return new ResponseEntity<>(reviewService.addReview(review), HttpStatus.CREATED);
    }




    @Operation(
            summary = DELETE_REVIEW_BY_ID,
            description = DELETE_REVIEW_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = REVIEW_DELETED),
            @ApiResponse(responseCode = NOT_FOUND, description = REVIEW_NOT_FOUND)})
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable (name="reviewId")@Parameter(name = "Review Id", description = "Review id", example = "1") Long reviewId) throws ReviewNotFoundException {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}