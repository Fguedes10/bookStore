package mindera.backendProject.bookStore.dto.order;

import java.time.LocalDate;

public record DownloadCreateDto(
        Long orderModelId,
        LocalDate downloadDate,
        String downloadLink
) {

}
