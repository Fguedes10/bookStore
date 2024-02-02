package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.PaymentCreateDto;
import mindera.backendProject.bookStore.dto.order.PaymentGetDto;
import mindera.backendProject.bookStore.exception.order.OrderItemAlreadyExistsException;
import mindera.backendProject.bookStore.exception.order.PaymentNotFoundException;
import mindera.backendProject.bookStore.model.Payment;

import java.util.List;

public interface PaymentService {
    List<PaymentGetDto> getAllPayments();

    PaymentGetDto getPayment(Long paymentId) throws PaymentNotFoundException;

    PaymentGetDto createPayment(PaymentCreateDto payment, Long paymentId) throws OrderItemAlreadyExistsException;

    Payment deletePayment(Long paymentId) throws PaymentNotFoundException;
}
