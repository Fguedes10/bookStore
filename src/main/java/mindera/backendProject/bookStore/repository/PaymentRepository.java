package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
