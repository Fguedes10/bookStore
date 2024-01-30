package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAll();

    Payment getPayment(Long paymentId);

    Payment createPayment(Payment payment);

    List<Payment> createPayments(List<Payment> payment);

    void deletePayment(Long paymentId);
}
