package az.matrix.aptek.ms.dao;

import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface MedicineDao {
    Medicine add(Medicine medicine);

    Medicine getById(Long id) throws NotFoundException;

    void delete(Long id);

    Medicine update(Medicine medicine);

    Page<Medicine> getAll(String medicineName, Long branchId, Float minPrice, Float maxPrice, Long categoryId,
            String dosage, Integer page, Integer limit);
}
