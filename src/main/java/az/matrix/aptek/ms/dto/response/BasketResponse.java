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
public class BasketResponse {
    private Long id;
    private MedicineResponse medicine;
    private Integer quantity;
    private Date createdAt;
}