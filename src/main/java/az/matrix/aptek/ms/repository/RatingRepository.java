package az.matrix.aptek.ms.repository;

import az.matrix.aptek.ms.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserIdAndMedicineId (Long userId, Long medicineId);

    Page<Rating> findByMedicineId (Long medicineId, Pageable pageable);

    List<Rating> findByMedicineId (Long medicineId);

    List<Rating> findByMedicineBranchId (Long branchId);
}
