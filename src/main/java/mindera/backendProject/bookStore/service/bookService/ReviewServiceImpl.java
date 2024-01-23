package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.ReviewCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public List<ReviewCreateDto> getAll() {
        return null;
    }

    @Override
    public ReviewCreateDto getReview(Long reviewId) {
        return null;
    }

    @Override
    public ReviewCreateDto add(ReviewCreateDto author) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
