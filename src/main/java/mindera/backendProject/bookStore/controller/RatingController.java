package mindera.backendProject.bookStore.controller;


import mindera.backendProject.bookStore.dtos.books.RatingCreateDto;

import mindera.backendProject.bookStore.exception.RatingNotFoundException;
import mindera.backendProject.bookStore.service.bookService.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }


    @GetMapping("/{ratingId")
    public ResponseEntity<RatingCreateDto> getRating(@PathVariable("ratingId") Long ratingId) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRating(ratingId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<RatingCreateDto> getRatingByValue(@PathVariable("ratingValue") int ratingValue) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRatingByValue(ratingValue), HttpStatus.OK);
    }




}
