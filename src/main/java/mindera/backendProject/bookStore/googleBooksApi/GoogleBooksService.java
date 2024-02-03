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

        // Log the raw API response
        String rawApiResponse = restTemplate.getForObject(apiUrl, String.class);

        // Your mapping logic here
        GoogleBookInfoDto googleBookInfoDto = mapToGoogleBookInfo(rawApiResponse);

        // Return the GoogleBookInfo object
        return googleBookInfoDto;
    }

    private GoogleBookInfoDto mapToGoogleBookInfo(String rawApiResponse) {
        // Parse JSON using org.json library
        JSONObject root = new JSONObject(rawApiResponse);
        JSONArray items = root.getJSONArray("items");

        if (items.length() > 0) {
            JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");

            // Extract fields from volumeInfo
            GoogleBookInfoDto googleBookInfoDto = new GoogleBookInfoDto();
            googleBookInfoDto.setTitle(volumeInfo.getString("title"));
            googleBookInfoDto.setAverageRating(volumeInfo.getDouble("averageRating"));
            googleBookInfoDto.setPageCount(volumeInfo.getInt("pageCount"));

            return googleBookInfoDto;
        }

        return null; // Handle the case when no book information is found
    }
}
