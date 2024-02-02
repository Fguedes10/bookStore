package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.dto.order.DownloadCreateDto;
import mindera.backendProject.bookStore.dto.order.DownloadGetDto;
import mindera.backendProject.bookStore.model.Download;
import mindera.backendProject.bookStore.model.OrderModel;

public class DownloadConverter {
    public static Download fromDtoToModel(DownloadCreateDto downloadCreateDto, OrderModel orderModel) {
        return Download.builder()
                .orderModel(orderModel)
                .downloadDate(downloadCreateDto.downloadDate())
                .downloadLink(downloadCreateDto.downloadLink())
                .build();
    }


    public static DownloadGetDto fromModelToDownloadGetDto(Download download) {
        return new DownloadGetDto(
                download.getId(),
                download.getDownloadDate());

    }
}
