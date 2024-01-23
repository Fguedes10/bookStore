package mindera.backendProject.bookStore.repository.transactionRepository;

import mindera.backendProject.bookStore.model.transaction.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
