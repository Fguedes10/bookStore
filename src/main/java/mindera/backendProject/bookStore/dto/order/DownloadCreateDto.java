package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DownloadCreateDto(

        Long orderItemId,
        LocalDateTime downloadDate

) {
}