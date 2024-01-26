package az.matrix.aptek.ms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullBasketResponse {
    private List<BasketResponse> baskets;
    private Integer quantity;
    private Float price;
}