package mindera.backendProject.bookStore.dto.order;

import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Payment;

import java.time.LocalDate;
import java.util.List;

public record OrderItemsCreateDto(
        Customer customer,
        List<Long> downloads,
        LocalDate requestDate,
        Payment payment

) {

}
