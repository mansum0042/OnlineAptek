package az.matrix.aptek.ms.dao.impl;

import az.matrix.aptek.ms.dao.MedicineDao;
import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicineDaoJpaImpl implements MedicineDao {
    private final MedicineRepository medicineRepository;
    private final MessageSource messageSource;

    @Override
    public Medicine add(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine getById(Long id) throws NotFoundException {
        return medicineRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
                .getMessage("medicine.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public void delete(Long id) {
        medicineRepository.deleteById(id);
    }

    @Override
    public Medicine update(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Page<Medicine> getAll(String medicineName, Long branchId, Float minPrice, Float maxPrice,
            Long categoryId, String dosage, Integer page, Integer limit) {
        //creating filter for medicine name and dosage
        Specification<Medicine> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.like(root.get("medicineName"),
                                "%" + Objects.requireNonNullElse(medicineName, "") + "%"),
                        criteriaBuilder.like(root.get("dosage"),
                                "%" + Objects.requireNonNullElse(dosage, "") + "%"));
        //check if branch id sent then add to filter
        if (branchId != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("branch").get("id"), branchId));
        //check min price or max price added to filter
        if (minPrice != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        if (maxPrice != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        //check if category id is not null then add to filter
        if (categoryId != null)
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category").get("id"), categoryId));

        //create pagination
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return medicineRepository.findAll(specification, pageable);
    }
}

