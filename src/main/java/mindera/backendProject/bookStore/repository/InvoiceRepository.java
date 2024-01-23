package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
