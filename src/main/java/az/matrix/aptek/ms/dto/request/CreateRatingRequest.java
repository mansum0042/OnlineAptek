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
public class CreateRatingRequest {
    @NotNull(message = "{rating.emptyRating}")
    private Integer rating;
    private String feedback;
}