package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.AuthorCreateDto;
import mindera.backendProject.bookStore.model.Author;


public class AuthorConverter {

    public static Author fromCreateDtoToModel(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.name())
                .build();
    }


    public static AuthorCreateDto fromModelToAuthorCreateDto(Author author) {
        return new AuthorCreateDto(
                author.getName());
    }
}
