package mindera.backendProject.bookStore.apiHandler;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/googleBooksApi")
public class RestfulApiController {

    private final RestApiService restApiService;

    public RestfulApiController(RestApiService restApiService){
        this.restApiService = restApiService;
    }


    @GetMapping("/")
    public ResponseEntity<List<RestApiModel>> getAllBooks(){
        List<RestApiModel> books = restApiService.getAllApiBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestApiModel> getBookById(@PathVariable Long id){
        RestApiModel book = restApiService.getApiBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/bookFromApi{title}/{author}")
    public ResponseEntity<String> requestBookDetails(@PathVariable ("title") String titleBook, @PathVariable ("author") String authorBook){
        String apiResponse = restApiService.requestBookDetails(titleBook, authorBook);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

   /* @PostMapping("/")
    public ResponseEntity<RestApiModel> saveBookToDb (@PathVariable HttpResponse<String> response){
        restApiService.saveToDb(response);
        return new ResponseEntity<>(RestfulApiRepository.save());
    }*/


    /*@PostMapping("/get-top-rated")
    public ResponseEntity<String> requestTopRatedBooks(){
        List<HttpResponse<String>> responseList = restApiService.requestTopRatedBooks();
        restApiService.saveTopRatedBooks(responseList);
    }*/


   /* @PostMapping("/get-top-rated")
    public ResponseEntity<List<String>> requestTopRatedBooks(@RequestBody ){
        restApiService.saveTopRatedBooks();

    }*/



}
