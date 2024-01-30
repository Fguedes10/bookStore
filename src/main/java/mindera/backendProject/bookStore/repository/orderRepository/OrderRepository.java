package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderItem, Long> {
}