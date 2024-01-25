package mindera.backendProject.bookStore.service;

import mindera.backendProject.bookStore.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    public List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(Long id);

    Payment createPayment(Payment payment);

}
