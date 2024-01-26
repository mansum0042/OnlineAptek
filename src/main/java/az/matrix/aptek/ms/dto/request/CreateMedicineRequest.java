package az.matrix.aptek.ms.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMedicineRequest {
    @NotBlank(message = "{medicine.emptyName}")
    private String medicineName;

    @NotNull(message = "{medicine.emptyQuantity}")
    @Min(value = 0, message = "{medicine.minQuantity}")
    private Integer quantity;

    @NotNull(message = "{medicine.emptyPrice}")
    @Min(value = 0, message = "{medicine.minPrice}")
    private Float price;

    @NotBlank(message = "{medicine.emptyDosage}")
    private String dosage;

    @NotNull(message = "{medicine.emptyCategory}")
    private Long categoryId;
}