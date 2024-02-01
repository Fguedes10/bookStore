package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.dto.order.OrderItemCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderItemGetDto;
import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.model.OrderModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderItemConverter {

    public static OrderItem fromCreateDtoToModel(OrderItemCreateDto orderItemCreateDto, Customer customer, Set<Book> books) {
        return OrderItem.builder()
                .customer(customer)
                .booksToPurchase(books)
                .amountToPay(orderItemCreateDto.amountToPay())
                .isOrdered(orderItemCreateDto.isOrdered())
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
