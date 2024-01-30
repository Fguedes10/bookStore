package mindera.backendProject.bookStore.dto.order;

import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Download;
import mindera.backendProject.bookStore.model.OrderItem;

import java.time.LocalDate;
import java.util.List;

public record OrderGetDto (

    List<OrderItemCreateDto> orderItem,
    CustomerCreateDto customer,
    List<DownloadCreateDto> download,
    InvoiceCreateDto invoice,
    List<BookCreateDto> book,
    LocalDate purchaseDate
            ){
}
