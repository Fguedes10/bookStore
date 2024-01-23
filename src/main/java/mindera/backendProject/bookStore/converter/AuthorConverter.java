package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dtos.books.AuthorCreateDto;
import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.model.Book;


public class AuthorConverter {

    public static Author fromCreateDtoToModel(AuthorCreateDto authorCreateDto, Book book) {
        return Author.builder()
                .name(authorCreateDto.name())
                .book(book)
                .build();
    }


    public static AuthorCreateDto fromModelToAuthorCreateDto(Author author) {
        return new AuthorCreateDto(
                author.getName(),
                author.getBook().getId());
    }
}
