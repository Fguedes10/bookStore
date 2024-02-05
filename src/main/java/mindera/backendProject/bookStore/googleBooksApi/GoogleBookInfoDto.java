package mindera.backendProject.bookStore.googleBooksApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GoogleBookInfoDto {

    private String title;
    private Integer pageCount;
    private Double averageRating;

}
