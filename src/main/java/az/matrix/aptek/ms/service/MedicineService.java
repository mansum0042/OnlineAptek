package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.request.CreateMedicineRequest;
import az.matrix.aptek.ms.dto.request.UpdateMedicineRequest;
import az.matrix.aptek.ms.dto.response.MedicineResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface MedicineService {
    MedicineResponse add(CreateMedicineRequest createMedicine) throws NotFoundException;

    MedicineResponse getById(Long id) throws NotFoundException;

    void delete(Long id) throws IllegalOperationException, NotFoundException;

    MedicineResponse update(Long medicineId, UpdateMedicineRequest updateMedicine) throws IllegalOperationException, NotFoundException;

    PageResponse<MedicineResponse> getAll(String medicineName, Long branchId, Float minPrice, Float maxPrice, Long categoryId,
            String dosage, Integer page, Integer limit);
}
