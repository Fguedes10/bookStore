package mindera.backendProject.bookStore.dto.book;

import java.time.LocalDate;
import java.util.List;
public record BookGetDto (


        String title,
        AuthorCreateDto author,
        PublisherCreateDto publisher,
        List<GenreCreateDto> genres,
        List<TranslationCreateDto> translations,
        int edition,
        int yearRelease,
        double price,
        List<ReviewCreateDto> reviews

) {
}
