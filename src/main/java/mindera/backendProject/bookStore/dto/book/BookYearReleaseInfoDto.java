package mindera.backendProject.bookStore.dto.book;

import mindera.backendProject.bookStore.model.Author;
import mindera.backendProject.bookStore.model.Publisher;

import java.util.List;

public record BookYearReleaseInfoDto(

        Long id,
        String title,
        AuthorCreateDto author,
        PublisherCreateDto publisher,
        Integer edition,
        List<GenreCreateDto> genreList,
        List<TranslationCreateDto> translationList,
        double price


) {
}
