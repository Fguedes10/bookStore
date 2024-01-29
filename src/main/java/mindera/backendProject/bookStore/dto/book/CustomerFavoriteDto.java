package mindera.backendProject.bookStore.dto.book;

import java.util.List;
public record CustomerFavoriteDto(
        List<Long> bookIds

) {

}
