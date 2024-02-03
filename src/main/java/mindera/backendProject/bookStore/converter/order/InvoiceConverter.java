package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.converter.book.*;
import mindera.backendProject.bookStore.converter.customer.CustomerConverter;
import mindera.backendProject.bookStore.dto.book.BookCreateDto;
import mindera.backendProject.bookStore.dto.book.BookGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.order.InvoiceCreateDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetByCustomerDto;
import mindera.backendProject.bookStore.dto.order.InvoiceGetDto;
import mindera.backendProject.bookStore.dto.order.OrderCreateDto;
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

    public static InvoiceCreateDto fromModelToInvoiceCreateDto(Invoice invoice) {
        return new InvoiceCreateDto(
                invoice.getCustomer().getId(),
                invoice.getOrderModel().getId(),
                invoice.getIssueDate(),
                invoice.getTotalAmount(),
                invoice.getVAT());

    }


    public static InvoiceGetDto fromModelToInvoiceGetDto(Invoice invoice) {
        return new InvoiceGetDto(
                invoice.getCustomer().getFirstName()+ " " + invoice.getCustomer().getLastName(),
                invoice.getOrderModel().getId(),
                invoice.getIssueDate(),
                invoice.getTotalAmount(),
                invoice.getInvoiceNumber(),
                invoice.getVAT());
    }

    public static InvoiceGetByCustomerDto fromModelToInvoiceGetByCustomerDto (Invoice invoice){
        return new InvoiceGetByCustomerDto(
                invoice.getId(),
                invoice.getIssueDate(),
                invoice.getInvoiceNumber(),
                invoice.getTotalAmount()
        );
    }
}
