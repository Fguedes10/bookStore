package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
