package az.matrix.aptek.ms.dao.impl;

import az.matrix.aptek.ms.dao.RatingDao;
import az.matrix.aptek.ms.entity.Rating;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingDaoJpaImpl implements RatingDao {
    private final RatingRepository ratingRepository;
    private final MessageSource messageSource;

    @Override
    public Rating add(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void remove(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public Rating getById(Long id) throws NotFoundException {
        return ratingRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage(
                "rating.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public Page<Rating> getRatings(Long medicineId, Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return ratingRepository.findByMedicineId(medicineId, pageable);
    }

    @Override
    public Rating getByUserIdAndMedicineId(Long userId, Long medicineId) throws NotFoundException {
        return ratingRepository.findByUserIdAndMedicineId(userId, medicineId).orElseThrow(NotFoundException::new);
    }
}
