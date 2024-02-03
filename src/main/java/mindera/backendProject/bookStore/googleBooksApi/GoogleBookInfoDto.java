package mindera.backendProject.bookStore.googleBooksApi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleBookInfoDto {

    private String title;
    private Integer pageCount;
    private Double averageRating;

}
