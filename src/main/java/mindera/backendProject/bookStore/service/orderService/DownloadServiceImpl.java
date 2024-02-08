package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.converter.order.DownloadConverter;
import mindera.backendProject.bookStore.dto.order.DownloadCreateDto;
import mindera.backendProject.bookStore.dto.order.DownloadGetDto;
import mindera.backendProject.bookStore.exception.order.DownloadNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.Download;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.repository.orderRepository.DownloadRepository;
import mindera.backendProject.bookStore.repository.orderRepository.OrderRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static mindera.backendProject.bookStore.util.Messages.*;

@Service
public class DownloadServiceImpl implements DownloadService {


    private final DownloadRepository downloadRepository;
    private final OrderRepository orderRepository;

    public DownloadServiceImpl(DownloadRepository downloadRepository, OrderRepository orderRepository) {
        this.downloadRepository = downloadRepository;
        this.orderRepository = orderRepository;
    }

    public static String generateDownloadLink(Long bookId) {
        String randomToken = generateRandomToken();
        return String.format("https://yourdomain.com/download/%d/%s", bookId, randomToken);
    }

    private static String generateRandomToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Override
    public List<DownloadGetDto> getAllDownloads() {
        List<Download> downloadList = downloadRepository.findAll();
        return downloadList.stream().map(DownloadConverter::fromModelToDownloadGetDto).toList();
    }

    @Override
    public DownloadGetDto getDownload(Long downloadId) throws DownloadNotFoundException {
        Optional<Download> downloadOptional = verifyDownloadExistsById(downloadId);
        return DownloadConverter.fromModelToDownloadGetDto(downloadOptional.get());
    }

    public List<DownloadGetDto> getDownloadsByOrder(Long orderModelId) throws OrderNotFoundException, DownloadNotFoundException {
        if ((orderModelId <= 0)) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + orderModelId + DOESNT_EXIST);
        }
        Optional<OrderModel> getOrder = orderRepository.findById(orderModelId);
        if (getOrder.isEmpty()) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + orderModelId + DOESNT_EXIST);
        }
        List<Download> findedDownloads = downloadRepository.findDownloadByOrder(orderModelId);
        if (findedDownloads.isEmpty()) {
            throw new DownloadNotFoundException(NO_DOWNLOAD_WITH_ORDER + orderModelId);
        }
        return downloadRepository.findDownloadByOrder(orderModelId)
                .stream()
                .map(DownloadConverter::fromModelToDownloadGetDto)
                .toList();
    }

    private Optional<Download> verifyDownloadExistsById(Long downloadId) throws DownloadNotFoundException {
        Optional<Download> downloadOptional = downloadRepository.findById(downloadId);
        if (downloadOptional.isEmpty()) {
            throw new DownloadNotFoundException(DOWNLOAD_WITH_ID + downloadId + DOESNT_EXIST);
        }
        return downloadOptional;
    }

    @Override
    public DownloadGetDto createDownload(DownloadCreateDto downloadCreateDto) throws OrderNotFoundException {
        Optional<OrderModel> checkIfOrderExists = orderRepository.findById(downloadCreateDto.orderModelId());
        if (checkIfOrderExists.isEmpty()) {
            throw new OrderNotFoundException(ORDERMODEL_WITH_ID + downloadCreateDto + DOESNT_EXIST);
        }
        Download downloadToSave = DownloadConverter.fromDtoToModel(downloadCreateDto, checkIfOrderExists.get());
        downloadRepository.save(downloadToSave);
        return DownloadConverter.fromModelToDownloadGetDto(downloadToSave);
    }
}
