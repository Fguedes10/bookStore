package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.model.Download;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DownloadService {
    List<Download> getAll();

    Download getDownload(Long downloadId);

    Download createDownload(Download download);

    List<Download> createDownloads(List<Download> download);

    void deleteDownload(Long downloadId);
}
