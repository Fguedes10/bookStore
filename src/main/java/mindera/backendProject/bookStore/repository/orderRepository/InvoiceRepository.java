package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(int invoiceNumber);

    List<Invoice> findInvoicesByCustomer(Long customerId);
}
