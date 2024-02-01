package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    Optional<OrderModel> findByPurchaseDate(LocalDate localDate);

    List<OrderModel> findOrderByCustomer(Long customerId);

    List<OrderModel> findOrderByBook(Long bookId);
}
