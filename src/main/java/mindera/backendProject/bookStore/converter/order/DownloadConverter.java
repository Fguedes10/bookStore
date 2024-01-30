package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.AuthorConverter;
import mindera.backendProject.bookStore.dto.order.DownloadCreateDto;
import mindera.backendProject.bookStore.model.*;

public class DownloadConverter {
    public static Download fromCreateDtoToModel(DownloadCreateDto downloadCreateDto, OrderModel orderModel) {
        return Download.builder()
                .orderModel(orderModel)
                .downloadDate(downloadCreateDto.downloadDate())
                .build();
    }

    public static DownloadCreateDto fromModelToDownloadCreateDto(Download download){
        return new DownloadCreateDto(
                OrderConverter.fromModelToOrderCreateDto(download.getOrderModel()),
                download.getDownloadDate()
        );
    }
}
