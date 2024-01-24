package mindera.backendProject.bookStore.converter;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookUpdateEditionDto;
import mindera.backendProject.bookStore.dto.book.BookUpdatePriceDto;
import mindera.backendProject.bookStore.model.*;

public class BookConverter {

    public static Book fromCreateDtoToModel(BookCreateDto bookCreateDto, Author author){
        return Book.builder()
                .title(bookCreateDto.title())
                .isbn(bookCreateDto.isbn())
                .author(author)
                .publisher(bookCreateDto.publisher())
                .genres(bookCreateDto.genres())
                .translations(bookCreateDto.translations())
                .reviews(bookCreateDto.reviews())
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
                book.getGenres(),
                book.getTranslations(),
                book.getReviews(),
                book.getEdition(),
                book.getReleaseDate(),
                book.getPrice()
        );
    }


    public static BookUpdateEditionDto fromModelToBookUpdateEditionDto (Book book) {
        return new BookUpdateEditionDto(
                book.getEdition()
        );
    }

    public static Book fromUpdateEditionDtoToModel (BookUpdateEditionDto bookUpdateEditionDto){
        return Book.builder()
                .edition(bookUpdateEditionDto.edition())
                .build();
    }


    public static BookUpdatePriceDto fromModelToBookUpdatePriceDto (Book book) {
        return new BookUpdatePriceDto(
                book.getPrice()
        );
    }

    public static Book fromUpdatePriceDtoToModel (BookUpdatePriceDto bookUpdatePriceDto){
        return Book.builder()
                .price(bookUpdatePriceDto.price())
                .build();
    }

}