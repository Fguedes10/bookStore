package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.AuthorConverter;
import mindera.backendProject.bookStore.dto.order.DownloadCreateDto;
import mindera.backendProject.bookStore.model.*;

public class DownloadConverter {
   /* public static Download fromCreateDtoToModel(DownloadCreateDto downloadCreateDto, OrderItem orderItem) {
        return Download.builder()
                .orderItem(orderItem)
                .downloadDate(downloadCreateDto.downloadDate())
                .build();
    }

    public static DownloadCreateDto fromModelToDownloadCreateDto(Download download){
        return new DownloadCreateDto(
                OrderItemConverter.fromModelToOrderITemCreateDto(download.getDownloadDate()),
                download.getDownloadDate()
        );
    }*/
}
