package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.model.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {
}
