package mindera.backendProject.bookStore.apiHandler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestfulApiRepository extends JpaRepository<RestApiModel, Long> {
}
