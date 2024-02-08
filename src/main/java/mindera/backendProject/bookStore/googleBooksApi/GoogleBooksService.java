package mindera.backendProject.bookStore.googleBooksApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GoogleBooksService {

    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private final GoogleBooksConfig googleBooksConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public GoogleBooksService(GoogleBooksConfig googleBooksConfig, RestTemplate restTemplate) {
        this.googleBooksConfig = googleBooksConfig;
        this.restTemplate = restTemplate;
    }

    public GoogleBookInfoDto getBookInfo(String title) {
        String apiUrl = GOOGLE_BOOKS_API_URL + "?q=title:" + title + "&key=" + googleBooksConfig.getApiKey();

        String rawApiResponse = restTemplate.getForObject(apiUrl, String.class);
        System.out.println(rawApiResponse);

        GoogleBookInfoDto googleBookInfoDto = mapToGoogleBookInfo(rawApiResponse);

        return googleBookInfoDto;
    }

    private GoogleBookInfoDto mapToGoogleBookInfo(String rawApiResponse) {
        JSONObject root = new JSONObject(rawApiResponse);
        JSONArray items = root.getJSONArray("items");

        if (!items.isEmpty()) {
            JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
            GoogleBookInfoDto googleBookInfoDto = new GoogleBookInfoDto();
            if (volumeInfo.has("averageRating")) {
                googleBookInfoDto.setAverageRating(volumeInfo.getDouble("averageRating"));
            } else {
                googleBookInfoDto.setAverageRating(0.0);
            }
            if (volumeInfo.has("pageCount")) {
                googleBookInfoDto.setPageCount(volumeInfo.getInt("pageCount"));
            } else {
                googleBookInfoDto.setPageCount(0);
            }
            googleBookInfoDto.setTitle(volumeInfo.getString("title"));
            return googleBookInfoDto;
        }
        return null;
    }
}
