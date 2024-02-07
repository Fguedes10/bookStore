package mindera.backendProject.bookStore.controller.book;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mindera.backendProject.bookStore.dto.book.RatingCreateDto;

import mindera.backendProject.bookStore.exception.book.RatingNotFoundException;
import mindera.backendProject.bookStore.service.bookService.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = RATING_TAG_NAME, description = RATING_TAG_DESCRIPTION)
@RestController
@RequestMapping("api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingServiceImpl ratingService;


    @Operation(
            summary = GET_RATING_BY_ID,
            description = GET_RATING_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = RATING_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = RATING_NOT_FOUND)})
    @GetMapping("/id/{ratingId}")
    public ResponseEntity<RatingCreateDto> getRating(@PathVariable("ratingId") Long ratingId) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRating(ratingId), HttpStatus.OK);
    }


    @Operation(
            summary = GET_RATING_BY_VALUE,
            description = GET_RATING_BY_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = RATING_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = RATING_NOT_FOUND)})
    @GetMapping("/value/{ratingValue}")
    public ResponseEntity<RatingCreateDto> getRatingByValue(@PathVariable("ratingValue") int ratingValue) throws RatingNotFoundException {
        return new ResponseEntity<>(ratingService.getRatingByValue(ratingValue), HttpStatus.OK);
    }




}
