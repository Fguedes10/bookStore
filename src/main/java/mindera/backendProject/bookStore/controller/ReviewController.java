package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.ReviewNotFoundException;
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


    @GetMapping("/")
    public ResponseEntity<List<ReviewCreateDto>> getReviews(){
        return ResponseEntity.ok(reviewService.getAll());
    }


    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewCreateDto> getReview(@PathVariable("reviewId") Long reviewId) throws ReviewNotFoundException{
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<ReviewCreateDto> add(@RequestBody ReviewCreateDto review) {
        ReviewCreateDto reviewDto = reviewService.add(review);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Review id", example = "1") Long id) throws ReviewNotFoundException {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}