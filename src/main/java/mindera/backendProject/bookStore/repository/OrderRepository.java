package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Request, Long> {
}
