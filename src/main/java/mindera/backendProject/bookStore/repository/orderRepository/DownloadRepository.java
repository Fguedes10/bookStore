package mindera.backendProject.bookStore.repository.orderRepository;

import mindera.backendProject.bookStore.model.Download;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepository extends JpaRepository<Download, Long> {
}
