package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record DownloadGetDto(
        Long id,
        LocalDate downloadDate
) {
}
