package mindera.backendProject.bookStore.apiHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/googleBooksApi")
public class RestApiController {


    private final RestApiService restApiService;

    public RestApiController(RestApiService restApiService){
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

/*   @PostMapping("/")
    public ResponseEntity<RestApiModel> saveBookToDb (@PathVariable HttpResponse<String> response){
       return new ResponseEntity<>(restApiService.saveToDb(response, HttpStatus.CREATED);
    }


    @PostMapping("/addTopRated/")
    public ResponseEntity<List<String>> requestTopRatedBooks(@RequestBody List<HttpResponse<String>> responseList){
        return new ResponseEntity<>(restApiService.saveTopRatedBooks(responseList, HttpStatus.CREATED);
    }*/



}
