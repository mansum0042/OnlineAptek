package az.matrix.aptek.ms.dao;

import az.matrix.aptek.ms.entity.Rating;
import az.matrix.aptek.ms.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface RatingDao {
    Rating add(Rating rating);

    Rating update(Rating rating);

    void remove(Long id);

    Rating getById(Long id) throws NotFoundException;

    Page<Rating> getRatings(Long medicineId, Integer page, Integer limit);

    Rating getByUserIdAndMedicineId(Long userId, Long medicineId) throws NotFoundException;
}
