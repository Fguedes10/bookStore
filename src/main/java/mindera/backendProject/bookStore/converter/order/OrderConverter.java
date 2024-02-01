package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByBookDto;
import mindera.backendProject.bookStore.dto.order.OrderGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.model.*;

import java.util.List;

public class OrderConverter {


    public static OrderModel fromCreateDtoToModel(OrderCreateDto orderCreateDto, Customer customer, List<Book> books) {
        return OrderModel.builder()
                .customer(customer)
                .books(books)
                .purchaseDate(orderCreateDto.purchaseDate())
                .build();
    }


    public static OrderGetDto fromModelToOrderGetDto(OrderModel orderModel) {
        return new OrderGetDto(
                CustomerConverter.fromEntityToCustomerGetDto(orderModel.getCustomer()),
                orderModel.getBooks().stream().map(BookConverter::fromModelToBookGetDto).toList(),
                orderModel.getPurchaseDate());
    }

    public static OrderGetByCustomerDto fromModelToOderGetByCustomerDto(OrderModel orderModel){
        return new OrderGetByCustomerDto(
                orderModel.getId(),
                orderModel.getPurchaseDate()
        );
    }

    public static OrderGetByBookDto fromModelToOderGetByBookDto(OrderModel orderModel){
        return new OrderGetByBookDto(
                orderModel.getId(),
                orderModel.getPurchaseDate()
        );
    }

}

