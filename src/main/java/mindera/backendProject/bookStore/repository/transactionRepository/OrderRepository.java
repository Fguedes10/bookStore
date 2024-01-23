package mindera.backendProject.bookStore.repository.transactionRepository;

import mindera.backendProject.bookStore.model.transaction.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<BookOrder, Long> {
}
