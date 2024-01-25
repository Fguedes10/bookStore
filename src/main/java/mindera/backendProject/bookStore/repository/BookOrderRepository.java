package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.model.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
}