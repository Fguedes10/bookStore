package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(int invoiceNumber);

    @Query("select i from Invoice i where i.customer.id = :customerId")
    List<Invoice> findInvoicesByCustomer(@Param("customerId") Long customerId);
}
