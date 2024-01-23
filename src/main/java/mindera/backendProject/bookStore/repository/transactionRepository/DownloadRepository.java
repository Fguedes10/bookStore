package mindera.backendProject.bookStore.repository.transactionRepository;

import mindera.backendProject.bookStore.model.transaction.Download;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepository extends JpaRepository<Download, Long> {
}
