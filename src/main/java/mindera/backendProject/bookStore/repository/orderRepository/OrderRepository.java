package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    Optional<OrderModel> findByPurchaseDate(LocalDate localDate);

    @Query("SELECT j FROM OrderModel j JOIN j.customer c WHERE c.id = :customerId")
    List<OrderModel> findOrderByCustomer(Long customerId);

    @Query("SELECT j FROM OrderModel j JOIN j.books b WHERE b.id = :bookId")
    List<OrderModel> getOrderModelsByBookId(@Param("bookId") Long bookId);
}

