package mindera.backendProject.bookStore.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.exception.order.DownloadAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.DownloadNotFoundException;
import mindera.backendProject.bookStore.exception.order.OrderAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.OrderNotFoundException;
import mindera.backendProject.bookStore.model.Download;
import mindera.backendProject.bookStore.model.OrderModel;
import mindera.backendProject.bookStore.service.orderService.DownloadServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Download", description = "Download endpoints")
@RestController
@RequestMapping("/api/v1/downloads")
public class DownloadController {

    private final DownloadServiceImpl downloadService;

    public DownloadController(DownloadServiceImpl downloadService) {
        this.downloadService = downloadService;
    }


    @Operation(
            summary = "Get all existing downloads",
            description = "Get all downloads"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get all downloads")})
    @GetMapping("/")
    public ResponseEntity<List<Download>> getDownloads(){
        return ResponseEntity.ok(downloadService.getAll());
    }



    @Operation(
            summary = "Get download by id",
            description = "Get download by id"
    )
    @GetMapping("/id/{downloadId}")
    public ResponseEntity<Download> getDownload(@PathVariable("downloadId")@Parameter(name = "Download Id", description = "Download id", example = "1" ) Long downloadId) throws DownloadNotFoundException {
        return new ResponseEntity<>(downloadService.getDownload(downloadId), HttpStatus.OK);
    }

    @Operation(
            summary = "Add new download",
            description = "Add new download"
    )
    @PostMapping("/")
    public ResponseEntity<Download> addNewDownload(@Valid @RequestBody Download download) throws DownloadAlreadyExistsException {
        return new ResponseEntity<>(downloadService.createDownload(download), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Add a list of new downloads",
            description = "Add a list of new downloads"
    )
    @PostMapping("/addMultipleDownloads")
    public ResponseEntity<List<Download>> addNewDownloads(@Valid @RequestBody List<Download> download) throws DownloadAlreadyExistsException{
        return new ResponseEntity<>(downloadService.createDownloads(download), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete download",
            description = "Delete download"
    )
    @DeleteMapping("/id/{downloadId}")
    public ResponseEntity<Download> deleteDownloadById(@PathVariable ("downloadId")@Parameter(name = "Download Id", description =  "Download id", example = "1") Long downloadId) throws DownloadNotFoundException {
        downloadService.deleteDownload(downloadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
