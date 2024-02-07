package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.DownloadCreateDto;
import mindera.backendProject.bookStore.dto.order.DownloadGetDto;
import mindera.backendProject.bookStore.exception.order.DownloadNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.service.orderService.DownloadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static mindera.backendProject.bookStore.util.Messages.*;

@Tag(name = DOWNLOAD_TAG_NAME, description = DOWNLOAD_TAG_DESCRIPTION)
@RestController
@RequestMapping("/api/v1/downloads")
public class DownloadController {

    @Autowired
    private DownloadServiceImpl downloadService;

    @Operation(
            summary = GET_ALL_EXIST_DOWNLOADS,
            description = GET_ALL_EXIST_DOWNLOADS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = DOWNLOADS_FOUND)})
    @GetMapping("/")
    public ResponseEntity<List<DownloadGetDto>> getDownloads() {
        return ResponseEntity.ok(downloadService.getAllDownloads());
    }


    @Operation(
            summary = GET_DOWNLOAD_BY_ID,
            description = GET_DOWNLOAD_BY_ID
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = DOWNLOAD_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = DOWNLOAD_NOT_FOUND)
    })
    @GetMapping("/id/{downloadId}")
    public ResponseEntity<DownloadGetDto> getDownload(@PathVariable("downloadId") @Parameter(name = "Download Id", description = "Download id", example = "1") Long downloadId) throws DownloadNotFoundException {
        return new ResponseEntity<>(downloadService.getDownload(downloadId), HttpStatus.OK);
    }


    @Operation(
            summary = GET_DOWNLOAD_BY_ORDER,
            description = GET_DOWNLOAD_BY_ORDER
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = DOWNLOAD_FOUND),
            @ApiResponse(responseCode = NOT_FOUND, description = DOWNLOAD_OR_ORDER_NOT_FOUND)
    })
    @GetMapping("/downloadByOrder/{customerId}")
    public ResponseEntity<List<DownloadGetDto>> getDownloadsByOrder(@PathVariable("orderModelId") @Parameter(name = "OrderModel Id", description = "OrderModel id",
            example = "1") Long orderModelId) throws OrderNotFoundException, DownloadNotFoundException {
        return new ResponseEntity<>(downloadService.getDownloadsByOrder(orderModelId), HttpStatus.OK);
    }


    @Operation(
            summary = ADD_NEW_DOWNLOAD,
            description = ADD_NEW_DOWNLOAD
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = DOWNLOAD_CREATED),
            @ApiResponse(responseCode = NOT_FOUND, description = ORDER_NOT_FOUND)
    })
    @PostMapping("/")
    public ResponseEntity<DownloadGetDto> addNewDownload(@Valid @RequestBody DownloadCreateDto downloadCreateDto) throws OrderNotFoundException {
        return new ResponseEntity<>(downloadService.createDownload(downloadCreateDto), HttpStatus.CREATED);
    }


}
