package mindera.backendProject.bookStore.converter.book;

import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.model.Translation;

import java.util.List;

public class TranslationConverter {

    public static Translation fromCreateDtoToModel(TranslationCreateDto translationCreateDto) {
        return Translation.builder()
                .name(translationCreateDto.name())
                .build();
    }


    public static TranslationCreateDto fromModelToTranslationCreateDto(Translation translation) {
        return new TranslationCreateDto(
                translation.getName());
    }

    public static List<TranslationCreateDto> fromModelToTranslationCreateDtoList(List<Translation> translationList) {
        return translationList.stream().map(TranslationConverter::fromModelToTranslationCreateDto).toList();
    }
}
