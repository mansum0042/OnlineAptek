package az.matrix.aptek.ms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineResponse {
    private Long id;
    private String medicineName;
    private BranchResponse branch;
    private Integer quantity;
    private Float price;
    private CategoryResponse category;
    private String dosage;
    private Date createdDate;
    private Float avgRating;
}