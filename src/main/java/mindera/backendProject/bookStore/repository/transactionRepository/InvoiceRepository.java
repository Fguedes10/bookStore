package mindera.backendProject.bookStore.repository.transactionRepository;

import mindera.backendProject.bookStore.model.transaction.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
