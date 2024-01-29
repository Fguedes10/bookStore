package mindera.backendProject.bookStore.dto.customer;

import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.model.Genre;

import java.util.List;

public record CustomerGetDto(

        String firstName,

        String lastName,
        String email,
        List<GenreCreateDto> favoriteGenre
) {


}
