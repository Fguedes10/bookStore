package mindera.backendProject.bookStore.controller.book;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mindera.backendProject.bookStore.dto.book.RatingCreateDto;

import mindera.backendProject.bookStore.exception.book.RatingNotFoundException;
import mindera.backendProject.bookStore.service.bookService.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingServiceImpl ratingService;


    @Operation(
            summary = "Get a rating by id",
            description = "Get a rating by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating found"),
            @ApiResponse(responseCode = "404", description = "Rating not found")})
    @GetMapping("/id/{ratingId}")
    public ResponseEntity<RatingCreateDto> getRating(@PathVariable("ratingId") Long ratingId) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRating(ratingId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get a rating by value",
            description = "Get a rating by value"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating found"),
            @ApiResponse(responseCode = "404", description = "Rating not found")})
    @GetMapping("/value/{ratingValue}")
    public ResponseEntity<RatingCreateDto> getRatingByValue(@PathVariable("ratingValue") int ratingValue) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRatingByValue(ratingValue), HttpStatus.OK);
    }




}
