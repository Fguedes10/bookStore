package mindera.backendProject.bookStore.dto.customer;

import mindera.backendProject.bookStore.dto.book.GenreCreateDto;

import java.util.List;

public record CustomerFavoriteGenresDto(

        List<GenreCreateDto> favoriteGenres

) {

}
