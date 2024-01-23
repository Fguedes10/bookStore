package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.LanguageCreateDto;
import mindera.backendProject.bookStore.model.Language;

public class LanguageConverter {

    public static Language fromCreateDtoToModel(LanguageCreateDto languageCreateDto) {
        return Language.builder()
                .name(languageCreateDto.name())
                .build();
    }


    public static LanguageCreateDto fromModelToLanguageCreateDto(Language language) {
        return new LanguageCreateDto(
                language.getName());
    }
}
