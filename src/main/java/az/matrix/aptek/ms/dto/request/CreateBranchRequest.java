package az.matrix.aptek.ms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBranchRequest {
    @NotBlank(message = "{branch.emptyName}")
    private String name;

    @NotBlank(message = "{branch.emptyDescription}")
    private String description;
}