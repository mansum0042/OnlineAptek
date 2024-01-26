package az.matrix.aptek.ms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMedicineRequest {
    private String medicineName;
    private Integer quantity;
    private Float price;
    private String dosage;
}