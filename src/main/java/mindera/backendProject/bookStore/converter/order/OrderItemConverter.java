package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.dto.order.OrderItemCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.OrderItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderItemConverter {

    public static OrderItem fromCreateDtoToModel(List<Book> books) {
        return OrderItem.builder()
                .booksToPurchase(books)
                .build();
    }


    public static OrderItemGetDto fromModelToOrderITemGetDto(OrderItem orderItem) {
        return new OrderItemGetDto(
                orderItem.getCustomer().getFirstName()+ " " + orderItem.getCustomer().getLastName(),
                orderItem.getBooksToPurchase().stream().map(BookConverter::fromModelToBookGetDto).collect(Collectors.toSet()),
                orderItem.getAmountToPay(),
                orderItem.isOrdered());
    }

}
