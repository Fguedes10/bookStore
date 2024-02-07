package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;


    @Operation(
            summary = "Get all existing reviews",
            description = "Get all existing reviews"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all reviews")})
    @GetMapping("/search")
    public ResponseEntity<List<ReviewCreateDto>> getReviews(@RequestParam (defaultValue = "0", required = false) int page,
                                                            @RequestParam (defaultValue = "10", required = false) int size,
                                                            @RequestParam(defaultValue = "commentDate") String searchTerm) {
        return ResponseEntity.ok(reviewService.getAll(page, size, searchTerm));
    }


    @Operation(
            summary = "Get review by id",
            description = "Get review by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get review"),
            @ApiResponse(responseCode = "404", description = "Review not found")})
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewCreateDto> getReview(@PathVariable("reviewId")@Parameter(name = "Review Id", description = "Review id", example = "1") Long reviewId) throws ReviewNotFoundException{
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }



    @Operation(
            summary = "Add new review",
            description = "Add new review"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created"),
            @ApiResponse(responseCode = "404", description = "Book not found")})
    @PostMapping("/")
    public ResponseEntity<ReviewGetDto> addnewReview(@RequestBody ReviewAddNewDto review) throws BookNotFoundException {
        return new ResponseEntity<>(reviewService.addReview(review), HttpStatus.CREATED);
    }




    @Operation(
            summary = "Delete review by id",
            description = "Delete review by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted"),
            @ApiResponse(responseCode = "404", description = "Review not found")})
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable (name="reviewId")@Parameter(name = "Review Id", description = "Review id", example = "1") Long reviewId) throws ReviewNotFoundException {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}