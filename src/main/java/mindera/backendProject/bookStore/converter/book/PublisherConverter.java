package mindera.backendProject.bookStore.converter.book;


import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;

import mindera.backendProject.bookStore.model.Publisher;

public class PublisherConverter {

    public static Publisher fromCreateDtoToModel(PublisherCreateDto publisherCreateDto) {
        return Publisher.builder()
                .name(publisherCreateDto.name())
                .build();
    }

    public static PublisherCreateDto fromModelToPublisherCreateDto(Publisher publisher) {
        return new PublisherCreateDto(
                publisher.getName());
    }
}
