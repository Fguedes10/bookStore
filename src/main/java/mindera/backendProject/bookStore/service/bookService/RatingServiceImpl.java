package mindera.backendProject.bookStore.service.bookService;

import mindera.backendProject.bookStore.dto.book.RatingCreateDto;
import mindera.backendProject.bookStore.model.Rating;
import mindera.backendProject.bookStore.repository.bookRepository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService{

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }
    @Override
    public RatingCreateDto getRating(Long ratingId) {
        return null;
    }

    @Override
    public RatingCreateDto getRatingByValue(int rantingValue) {
        return null;
    }
}
