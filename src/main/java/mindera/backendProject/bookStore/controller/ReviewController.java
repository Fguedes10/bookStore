package mindera.backendProject.bookStore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import mindera.backendProject.bookStore.dtos.books.ReviewCreateDto;
import mindera.backendProject.bookStore.exception.ReviewNotFoundException;
import mindera.backendProject.bookStore.service.bookService.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }


    @GetMapping("/")
    public ResponseEntity<List<ReviewCreateDto>> getReviews(){
        return ResponseEntity.ok(reviewService.getAll());
    }


    @GetMapping("/{reviewId")
    public ResponseEntity<ReviewCreateDto> getReview(@PathVariable("reviewId") Long reviewId) throws ReviewNotFoundException{
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<ReviewCreateDto> add(@RequestBody ReviewCreateDto author) {
        ReviewCreateDto reviewDto = reviewService.add(author);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name= "id", description = "Review id", example = "1") Long id) throws ReviewNotFoundException {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}