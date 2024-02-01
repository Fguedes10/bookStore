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
                .isSuccessful(paymentCreateDto.isSuccessful())
                .build();
    }

    public static Payment fromPaymentGetDtoToModel(PaymentGetDto paymentGetDto){
        return Payment.builder()
                .paymentDate(paymentGetDto.paymentDate())
                .amount(paymentGetDto.amount())
                .isSuccessful(paymentGetDto.isSuccessful())
                .build();
    }

    public static PaymentGetDto fromModelToPaymentGetDto(Payment payment){
        return new PaymentGetDto(
                payment.getPaymentDate(),
                payment.getAmount(),
                payment.isSuccessful());
    }

}
