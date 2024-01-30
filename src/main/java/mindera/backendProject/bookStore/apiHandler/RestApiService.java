package mindera.backendProject.bookStore.apiHandler;

import mindera.backendProject.bookStore.exception.RestApiModelNotFoundException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.List;

/*@Service
public class RestApiService {

    private final RestfulApiRepository restfulApiRepository;

    public RestApiService(RestfulApiRepository restfulApiRepository) {
        this.restfulApiRepository = restfulApiRepository;
    }

    private static final String GET_BOOKS_BY_TITLE_AND_AUTHOR = "https://www.googleapis.com/books/v1/volumes?q={title}+inauthor:{author}&key=AIzaSyAOE8cvzjg2UkmUZB-20YL4ssEjPlhcb7s";
    private final RestTemplate restTemplate = new RestTemplate();


    public String requestBookDetails(String titleBook, String authorBook) {
        String apiUrl = GET_BOOKS_BY_TITLE_AND_AUTHOR.replace("{title}", titleBook).replace("{author}", authorBook);
        return restTemplate.getForObject(apiUrl, String.class);
    }

    public void saveToDb(HttpResponse<String> response) {
        JSONObject responseBody = new JSONObject(response.body());
        System.out.println(responseBody);


            String title = responseBody.getJSONObject("title").getString("title");
            String author = responseBody.getJSONObject("author").getString("author");
            String bookCoverUrl = responseBody.getJSONObject("title").getJSONObject("cover").getString("url");
            double googleRating = responseBody.getJSONObject("rating").getDouble("rating");
            int numberOfPages = responseBody.getJSONObject("pages").getInt("pages");


            RestApiModel book = RestApiModel.builder()
                    .title(title)
                    .author(author)
                    .bookCoverUrl(bookCoverUrl)
                    .googleRating(googleRating)
                    .numberOfPages(numberOfPages)
                    .build();

            restfulApiRepository.save(book);
    }

    public void saveTopRatedBooks(List<HttpResponse<String>> responseList){
        for(HttpResponse<String> response : responseList){
            JSONObject responseBody = new JSONObject(response.body());
            System.out.println(responseBody);

            String title = responseBody.getJSONObject("title").getString("title");
            String author = responseBody.getJSONObject("author").getString("author");
            String bookCoverUrl = responseBody.getJSONObject("title").getJSONObject("cover").getString("url");
            double googleRating = responseBody.getJSONObject("rating").getDouble("rating");
            int numberOfPages = responseBody.getJSONObject("pages").getInt("pages");

            RestApiModel book = RestApiModel.builder()
                    .title(title)
                    .author(author)
                    .bookCoverUrl(bookCoverUrl)
                    .googleRating(googleRating)
                    .numberOfPages(numberOfPages)
                    .build();

            restfulApiRepository.save(book);
        }
    }

    public List<RestApiModel> getAllApiBooks() {
        return restfulApiRepository.findAll();
    }

    public RestApiModel getApiBookById(Long id) {
        return restfulApiRepository.findById(id).orElseThrow(RestApiModelNotFoundException::new);
    }


    public List<RestApiModel> getBooksById(List<Long> ids){
        return restfulApiRepository.findAllById(ids);
    }
}*/
