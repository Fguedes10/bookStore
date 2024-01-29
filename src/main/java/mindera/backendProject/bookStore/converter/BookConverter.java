package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.*;
import mindera.backendProject.bookStore.model.*;

import java.util.ArrayList;
import java.util.List;

public class BookConverter {

    public static Book fromCreateDtoToModel(BookCreateDto bookCreateDto, Author author, Publisher publisher, List<Genre> genreList, List<Translation> translationList) {
        return Book.builder()
                .title(bookCreateDto.title())
                .isbn(bookCreateDto.isbn())
                .author(author)
                .publisher(publisher)
                .genre(genreList)
                .translation(translationList)
                .edition(bookCreateDto.edition())
                .yearRelease(bookCreateDto.yearRelease())
                .price(bookCreateDto.price())
                .build();
    }


    public static BookGetDto fromModelToBookGetDto(Book book) {
        return new BookGetDto(
                book.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book.getPublisher()),
                book.getGenre().stream().map(GenreConverter::fromModelToGenreCreateDto).toList(),
                book.getTranslation().stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList(),
                book.getEdition(),
                book.getYearRelease(),
                book.getPrice(),
                book.getReview().stream().map(ReviewConverter::fromModelToReviewCreateDto).toList()
        );
    }


    public static BookUpdateEditionDto fromModelToBookUpdateEditionDto(Book book) {
        return new BookUpdateEditionDto(
                book.getEdition()
        );
    }

    public static Book fromUpdateEditionDtoToModel(BookUpdateEditionDto bookUpdateEditionDto) {
        return Book.builder()
                .edition(bookUpdateEditionDto.edition())
                .build();
    }


    public static BookUpdatePriceDto fromModelToBookUpdatePriceDto(Book book) {
        return new BookUpdatePriceDto(
                book.getPrice()
        );
    }

    public static Book fromUpdatePriceDtoToModel(BookUpdatePriceDto bookUpdatePriceDto) {
        return Book.builder()
                .price(bookUpdatePriceDto.price())
                .build();
    }

}
