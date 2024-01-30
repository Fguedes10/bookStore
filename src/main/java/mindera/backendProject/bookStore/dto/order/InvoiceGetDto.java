package mindera.backendProject.bookStore.dto.order;

import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;

import java.time.LocalDate;

public record InvoiceGetDto (
        CustomerGetDto customer,
        OrderCreateDto order,
        LocalDate issueDate,
        double totalAmount,
        double VAT
) {


}
