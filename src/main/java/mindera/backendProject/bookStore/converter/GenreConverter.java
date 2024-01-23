package mindera.backendProject.bookStore.converter;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.model.Genre;

public class GenreConverter {

    public static Genre fromCreateDtoToModel(GenreCreateDto genreCreateDto) {
        return Genre.builder()
                .name(genreCreateDto.name())
                .build();
    }


    public static GenreCreateDto fromModelToGenreCreateDto(Genre genre) {
        return new GenreCreateDto(
                genre.getName());
    }
}