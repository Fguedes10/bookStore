package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.dto.order.OrderGetDto;
import mindera.backendProject.bookStore.model.*;

import java.util.List;

public class OrderConverter {


    public static OrderModel fromCreateDtoToModel(OrderCreateDto orderCreateDto, Customer customer, Invoice invoice, List<Book> book) {
        return OrderModel.builder()
                .customer(customer)
                .invoice(invoice)
                .books(book)
                .purchaseDate(orderCreateDto.purchaseDate())
                .build();
    }

    public static OrderCreateDto fromModelToOrderCreateDto(OrderModel orderModel) {
        return new OrderCreateDto(
                orderModel.getCustomer().getId(),
                orderModel.getInvoice().getId(),
                orderModel.getBooks(),
                orderModel.getPurchaseDate()
        );
    }

    public static OrderGetDto fromModelToOrderGetDto(OrderModel orderModel) {
        return new OrderGetDto(
                CustomerConverter.fromEntityToCustomerGetDto(orderModel.getCustomer()),
                InvoiceConverter.fromModelToInvoiceCreateDto(orderModel.getInvoice()),
                orderModel.getBooks().stream().map(BookConverter::fromModelToBookGetDto).toList(),
                orderModel.getPurchaseDate());
    }
}

