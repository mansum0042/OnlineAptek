package az.matrix.aptek.ms.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBasketRequest {
    @NotNull(message = "{basket.emptyQuantity}")
    private Integer quantity;
}