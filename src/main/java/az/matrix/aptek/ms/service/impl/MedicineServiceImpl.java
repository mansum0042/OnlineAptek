package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.config.SecurityConfig;
import az.matrix.aptek.ms.dao.BranchDao;
import az.matrix.aptek.ms.dao.CategoryDao;
import az.matrix.aptek.ms.dao.MedicineDao;
import az.matrix.aptek.ms.dao.UserDao;
import az.matrix.aptek.ms.dto.request.CreateMedicineRequest;
import az.matrix.aptek.ms.dto.request.UpdateMedicineRequest;
import az.matrix.aptek.ms.dto.response.MedicineResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.entity.Medicine;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.mapper.MedicineMapper;
import az.matrix.aptek.ms.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicineServiceImpl implements MedicineService {
    private final MedicineDao medicineDao;
    private final MessageSource messageSource;
    private final SecurityConfig securityConfig;
    private final MedicineMapper medicineMapper;
    private final BranchDao branchDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Override
    public MedicineResponse add(CreateMedicineRequest createMedicine) throws NotFoundException {
        Medicine medicine = medicineMapper.map(createMedicine);
        medicine.setBranch(branchDao.getByUser(userDao.getLoggedInUser()));
        medicine.setCategory(categoryDao.getById(createMedicine.getCategoryId()));
        medicine.setCreatedDate(new Date());

        medicine = medicineDao.add(medicine);

        return medicineMapper.map(medicine);
    }

    @Override
    public MedicineResponse getById(Long id) throws NotFoundException {
        Medicine medicine = medicineDao.getById(id);
        return medicineMapper.map(medicine);
    }

    @Override
    public void delete(Long id) throws IllegalOperationException, NotFoundException {
        Medicine medicine = medicineDao.getById(id);

        if (!medicine.getBranch().getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("medicine.forbiddenDelete",
                    null, LocaleContextHolder.getLocale()));
        medicineDao.delete(id);
    }

    @Override
    public MedicineResponse update(Long medicineId, UpdateMedicineRequest updateMedicine)
            throws IllegalOperationException, NotFoundException {
        Medicine medicine = medicineDao.getById(medicineId);

        medicineMapper.map(updateMedicine, medicine);

        if (!medicine.getBranch().getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("medicine.forbiddenUpdate",
                    null, LocaleContextHolder.getLocale()));

        medicine = medicineDao.update(medicine);

        return medicineMapper.map(medicine);
    }

    @Override
    public PageResponse<MedicineResponse> getAll(String medicineName, Long branchId, Float minPrice, Float maxPrice,
            Long categoryId, String dosage, Integer page, Integer limit) {
        Page<Medicine> medicines = medicineDao
                .getAll(medicineName, branchId, minPrice, maxPrice, categoryId, dosage, page, limit);

        return medicineMapper.map(medicines);
    }
}

