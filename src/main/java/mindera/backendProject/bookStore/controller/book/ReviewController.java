package mindera.backendProject.bookStore.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.book.ReviewNotFoundException;
import mindera.backendProject.bookStore.service.bookService.ReviewServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewService;


    public ReviewController(ReviewServiceImpl reviewService){
        this.reviewService = reviewService;
    }


    @Operation(
            summary = "Get all existing reviews",
            description = "Get all existing reviews"
    )
    @GetMapping("/")
    public ResponseEntity<List<ReviewCreateDto>> getReviews(){
        return ResponseEntity.ok(reviewService.getAll());
    }


    @Operation(
            summary = "Get review by id",
            description = "Get review by id"
    )
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewCreateDto> getReview(@PathVariable("reviewId")@Parameter(name = "Review Id", description = "Review id", example = "1") Long reviewId) throws ReviewNotFoundException{
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }


   /* @PostMapping("/")
    public ResponseEntity<ReviewCreateDto> add(@RequestBody ReviewCreateDto review) {
        ReviewCreateDto reviewDto = reviewService.add(review);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }*/




    @Operation(
            summary = "Delete review by id",
            description = "Delete review by id"
    )
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable (name="reviewId")@Parameter(name = "Review Id", description = "Review id", example = "1") Long reviewId) throws ReviewNotFoundException {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}