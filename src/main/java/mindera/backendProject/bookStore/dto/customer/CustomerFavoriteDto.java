package mindera.backendProject.bookStore.dto.customer;

import java.util.List;
public record CustomerFavoriteDto(
        List<Long> bookIds

) {

}
