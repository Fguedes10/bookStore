package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.model.Translation;

public class TranslationConverter {

    public static Translation fromCreateDtoToModel(TranslationCreateDto translationCreateDto) {
        return Translation.builder()
                .name(translationCreateDto.name())
                .build();
    }


    public static TranslationCreateDto fromModelToGenreCreateDto(Translation translation) {
        return new TranslationCreateDto(
                translation.getName());
    }
}
