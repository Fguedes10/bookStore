package mindera.backendProject.bookStore.dto.book;

public record BookGetByTranslationDto(

        Long id,
        String title,
        String authorName,
        String publisherName,
        double price


) {
}
