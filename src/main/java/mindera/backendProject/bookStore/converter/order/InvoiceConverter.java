package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.model.*;

import java.util.List;

public class InvoiceConverter {

    public static Invoice fromCreateDtoToModel(InvoiceCreateDto invoiceCreateDto, Customer customer, OrderModel orderModel) {
        return Invoice.builder()
                .customer(customer)
                .orderModel(orderModel)
                .issueDate(invoiceCreateDto.issueDate())
                .totalAmount(invoiceCreateDto.totalAmount())
                .VAT(invoiceCreateDto.VAT())
                .build();
    }

    public static InvoiceCreateDto fromModelToInvoiceGetDto(Invoice invoice) {
        return new InvoiceCreateDto(
                CustomerConverter.fromEntityToCustomerGetDto(invoice.getCustomer()),
                OrderConverter.fromModelToOrderCreateDto(invoice.getOrderModel()),
                invoice.getIssueDate(),
                invoice.getTotalAmount(),
                invoice.getVAT());

    }
}
