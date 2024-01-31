package mindera.backendProject.bookStore.converter.order;

import mindera.backendProject.bookStore.dto.order.PaymentCreateDto;
import mindera.backendProject.bookStore.dto.order.PaymentGetDto;
import mindera.backendProject.bookStore.model.OrderItem;
import mindera.backendProject.bookStore.model.Payment;

public class PaymentConverter {




    public static Payment fromPaymentCreateDtoToModel (PaymentCreateDto paymentCreateDto, OrderItem orderItem){
        return Payment.builder()
                .orderItem(orderItem)
                .paymentDate(paymentCreateDto.paymentDate())
                .amount(paymentCreateDto.amount())
                .build();
    }

    public static Payment fromPaymentGetDtoToModel(PaymentGetDto paymentCreateDto){
        return Payment.builder()
                .paymentDate(paymentCreateDto.paymentDate())
                .amount(paymentCreateDto.amount())
                .build();
    }

    public static PaymentGetDto fromModelToPAymentGetDto(Payment payment){
        return new PaymentGetDto(
                payment.getPaymentDate(),
                payment.getAmount());
    }

}
