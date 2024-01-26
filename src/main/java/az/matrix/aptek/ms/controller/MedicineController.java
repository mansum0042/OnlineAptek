package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.CreateMedicineRequest;
import az.matrix.aptek.ms.dto.request.UpdateMedicineRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.MedicineResponse;
import az.matrix.aptek.ms.dto.response.PageResponse;
import az.matrix.aptek.ms.exception.IllegalOperationException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<PageResponse<MedicineResponse>> getAll(
            @RequestParam(required = false) String medicineName,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String dosage,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<MedicineResponse> medicines = medicineService.getAll(medicineName, branchId, minPrice, maxPrice, categoryId,
                dosage, page, limit);
        return ResponseEntity.ok(medicines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MedicineResponse>> getById(
            @PathVariable Long id) throws NotFoundException {
        MedicineResponse medicineResponse = medicineService.getById(id);

        BaseResponse<MedicineResponse> baseResponse = BaseResponse.<MedicineResponse>builder()
                .data(medicineResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public ResponseEntity<BaseResponse<MedicineResponse>> creatMedicine(@RequestBody @Valid CreateMedicineRequest request)
            throws NotFoundException {
        MedicineResponse medicineResponse = medicineService.add(request);

        BaseResponse<MedicineResponse> baseResponse = BaseResponse.<MedicineResponse>builder()
                .data(medicineResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public ResponseEntity<BaseResponse<Void>> deleteMedicine(
            @PathVariable Long id)
            throws NotFoundException, IllegalOperationException {
        medicineService.delete(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMsg(messageSource
                .getMessage("medicine.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public ResponseEntity<BaseResponse<MedicineResponse>> updateMedicine(
            @PathVariable Long id,
            @RequestBody UpdateMedicineRequest request)
            throws NotFoundException, IllegalOperationException {
        MedicineResponse medicineResponse = medicineService.update(id, request);

        BaseResponse<MedicineResponse> baseResponse = BaseResponse.<MedicineResponse>builder()
                .data(medicineResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }
}