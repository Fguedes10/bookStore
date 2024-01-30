package mindera.backendProject.bookStore.converter.book;
import mindera.backendProject.bookStore.dto.book.GenreCreateDto;
import mindera.backendProject.bookStore.model.Genre;

import java.util.List;

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



    public static List<GenreCreateDto> fromEntityToCreateDto(List<Genre> genreList){
        return genreList.stream().map(GenreConverter::fromModelToGenreCreateDto).toList();

    }
}