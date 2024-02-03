package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.DownloadCreateDto;
import mindera.backendProject.bookStore.dto.order.DownloadGetDto;
import mindera.backendProject.bookStore.exception.order.DownloadNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;

import java.util.List;


public interface DownloadService {

    List<DownloadGetDto> getAllDownloads();

    DownloadGetDto getDownload(Long downloadId) throws DownloadNotFoundException;

    DownloadGetDto createDownload(DownloadCreateDto downloadCreateDto) throws DownloadNotFoundException, OrderNotFoundException;

}
