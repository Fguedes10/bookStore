package mindera.backendProject.bookStore.converter.book;

import mindera.backendProject.bookStore.dto.book.*;
import mindera.backendProject.bookStore.model.*;

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
                book.getRating(),
                book.getPageCount(),
                book.getReview().stream().map(ReviewConverter::fromModelToReviewCreateDto).toList()
        );
    }

    public static BookGetNewBookDto fromModelToBookGetNewBookDto(Book book) {
        return new BookGetNewBookDto(
                book.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book.getPublisher()),
                book.getGenre().stream().map(GenreConverter::fromModelToGenreCreateDto).toList(),
                book.getTranslation().stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList(),
                book.getEdition(),
                book.getYearRelease(),
                book.getPrice(),
                book.getRating(),
                book.getPageCount()
        );
    }


    public static BookUpdateEditionDto fromModelToBookUpdateEditionDto(Book book) {
        return new BookUpdateEditionDto(
                book.getEdition()
        );
    }


    public static BookUpdatePriceDto fromModelToBookUpdatePriceDto(Book book) {
        return new BookUpdatePriceDto(
                book.getPrice()
        );
    }


    public static List<BookGetDto> fromModelToBookGetFavoriteBooksDto(List<Book> bookList) {
        return bookList.stream().map(BookConverter::fromModelToBookGetDto).toList();
    }

    public static BookYearReleaseInfoDto fromModelToBookYearReleaseInfoDto(Book book) {
        return new BookYearReleaseInfoDto(
                book.getId(),
                book.getTitle(),
                AuthorConverter.fromModelToAuthorCreateDto(book.getAuthor()),
                PublisherConverter.fromModelToPublisherCreateDto(book.getPublisher()),
                book.getEdition(),
                GenreConverter.fromEntityToCreateDto(book.getGenre()),
                TranslationConverter.fromModelToTranslationCreateDtoList(book.getTranslation()),
                book.getPrice()
        );
    }

    public static BookGetByTranslationDto fromModelToBookGetByTranslationDto(Book book) {
        return new BookGetByTranslationDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getPublisher().getName(),
                book.getPrice()
        );
    }

}
