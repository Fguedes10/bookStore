package mindera.backendProject.bookStore.controller;


import mindera.backendProject.bookStore.dto.book.RatingCreateDto;

import mindera.backendProject.bookStore.exception.RatingNotFoundException;
import mindera.backendProject.bookStore.service.bookService.RatingService;
import mindera.backendProject.bookStore.service.bookService.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ratings")
public class RatingController {

    private final RatingServiceImpl ratingService;


    public RatingController(RatingServiceImpl ratingService){
        this.ratingService = ratingService;
    }


    @GetMapping("/id/{ratingId}")
    public ResponseEntity<RatingCreateDto> getRating(@PathVariable("ratingId") Long ratingId) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRating(ratingId), HttpStatus.OK);
    }

    @GetMapping("/value/{ratingValue}")
    public ResponseEntity<RatingCreateDto> getRatingByValue(@PathVariable("ratingValue") int ratingValue) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRatingByValue(ratingValue), HttpStatus.OK);
    }




}
