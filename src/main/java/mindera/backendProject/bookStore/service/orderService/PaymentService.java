package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.dto.order.PaymentGetDto;
import mindera.backendProject.bookStore.exception.order.PaymentNotFoundException;
import mindera.backendProject.bookStore.model.Payment;

import java.util.List;

public interface PaymentService {
    List<PaymentGetDto> getAllPayments();

    PaymentGetDto getPayment(Long paymentId) throws PaymentNotFoundException;

    Payment createPayment(Payment payment);

    List<Payment> createPayments(List<Payment> payment);

    void deletePayment(Long paymentId);
}
