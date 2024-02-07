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

@Tag(name = "Download", description = "Download endpoints")
@RestController
@RequestMapping("/api/v1/downloads")
public class DownloadController {

    @Autowired
    private DownloadServiceImpl downloadService;

    @Operation(
            summary = "Get all existing downloads",
            description = "Get all downloads"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all downloads")})
    @GetMapping("/")
    public ResponseEntity<List<DownloadGetDto>> getDownloads() {
        return ResponseEntity.ok(downloadService.getAllDownloads());
    }


    @Operation(
            summary = "Get download by id",
            description = "Get download by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get download by id"),
            @ApiResponse(responseCode = "404", description = "Download not found")
    })
    @GetMapping("/id/{downloadId}")
    public ResponseEntity<DownloadGetDto> getDownload(@PathVariable("downloadId") @Parameter(name = "Download Id", description = "Download id", example = "1") Long downloadId) throws DownloadNotFoundException {
        return new ResponseEntity<>(downloadService.getDownload(downloadId), HttpStatus.OK);
    }


    @Operation(
            summary = "Get downloads by order",
            description = "Get downloads by order"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get downloads by order"),
            @ApiResponse(responseCode = "404", description = "Download not found or Order not found")
    })
    @GetMapping("/downloadByOrder/{customerId}")
    public ResponseEntity<List<DownloadGetDto>> getDownloadsByOrder(@PathVariable("orderModelId") @Parameter(name = "OrderModel Id", description = "OrderModel id",
            example = "1") Long orderModelId) throws OrderNotFoundException, DownloadNotFoundException {
        return new ResponseEntity<>(downloadService.getDownloadsByOrder(orderModelId), HttpStatus.OK);
    }


    @Operation(
            summary = "Add new download",
            description = "Add new download"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Download created"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/")
    public ResponseEntity<DownloadGetDto> addNewDownload(@Valid @RequestBody DownloadCreateDto downloadCreateDto) throws OrderNotFoundException {
        return new ResponseEntity<>(downloadService.createDownload(downloadCreateDto), HttpStatus.CREATED);
    }


}
