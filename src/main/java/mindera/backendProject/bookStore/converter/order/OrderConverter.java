package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.BookConverter;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
import mindera.backendProject.bookStore.model.*;

import java.util.List;

public class OrderConverter {




    public static OrderModel fromModelCreateDtoToModel(OrderCreateDto orderCreateDto, List<OrderItem> orderItem, Customer customer, List<Download> download, Invoice invoice, List<Book> book){
        return OrderModel.builder()
                .orderItems(orderItem)
                .customer(customer)
                .downloads(download)
                .invoice(invoice)
                .books(book)
                .purchaseDate(orderCreateDto.purchaseDate())
                .build();
    }

    public static OrderCreateDto fromModelToOrderCreateDto(OrderModel orderModel){
        return new OrderCreateDto(
                orderModel.getOrderItems().stream().map(OrderITemConverter::fromModelToOrderItemsCreateDto).toList(),
                CustomerConverter.fromEntityToCustomerGetDto(orderModel.getCustomer()),
                orderModel.getDownloads().stream().map(DownloadConverter::fromModelToDownloadCreateDto).toList(),
                InvoiceConverter.fromModelToInvoiceCreateDto(orderModel.getInvoice()),
                orderModel.getBooks().stream().map(BookConverter::fromModelToBookGetDto).toList(),
                orderModel.getPurchaseDate());

    }
}
