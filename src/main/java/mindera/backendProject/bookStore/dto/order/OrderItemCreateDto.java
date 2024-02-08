package mindera.backendProject.bookStore.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import mindera.backendProject.bookStore.model.Book;

import java.util.List;
import java.util.Set;

import static mindera.backendProject.bookStore.util.Messages.*;

public record OrderItemCreateDto(

       @NotEmpty(message = EMPTY_FIELD)
       List<Long> books

) {





}
