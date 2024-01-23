package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.model.Book;

public class BookConverter {

    public static Book fromCreateDtoToModel(BookCreateDto bookCreateDto){
        return Book.builder()
                .title(bookCreateDto.title())
                .isbn(bookCreateDto.isbn())
               // .author(bookCreateDto.author())
                .publisher(bookCreateDto.publisher())
                //.genre(bookCreateDto.genre())
                //.language(bookCreateDto.language())
                //.translation(bookCreateDto.translation())
                //.review((bookCreateDto.review()))
                .edition(bookCreateDto.edition())
                .releaseDate(bookCreateDto.releaseDate())
                .price(bookCreateDto.price())
                .build();
    }

    public static BookCreateDto fromModelToBookCreateDto(Book book) {
        return new BookCreateDto(
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor().getId(),
                book.getPublisher(),
                book.getGenres().getFirst().getId(),
                book.getLanguage().getId(),
                book.getTranslations().getFirst().getId(),
                book.getReview().getFirst().getId(),
                book.getEdition(),
                book.getReleaseDate(),
                book.getPrice()
        );
    }


}
