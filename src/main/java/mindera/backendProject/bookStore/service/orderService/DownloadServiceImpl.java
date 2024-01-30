package mindera.backendProject.bookStore.service.orderService;

import mindera.backendProject.bookStore.model.Download;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService {
    @Override
    public List<Download> getAll() {
        return null;
    }

    @Override
    public Download getDownload(Long downloadId) {
        return null;
    }

    @Override
    public Download createDownload(Download download) {
        return null;
    }

    @Override
    public List<Download> createDownloads(List<Download> download) {
        return null;
    }

    @Override
    public void deleteDownload(Long downloadId) {

    }

    public static String generateDownloadLink(Long bookId) {
        // Combine book ID and a random token to create a unique download link
        String randomToken = generateRandomToken();
        return String.format("https://yourdomain.com/download/%d/%s", bookId, randomToken);
    }

    private static String generateRandomToken() {
        // Generate a secure random token using Base64 encoding
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
