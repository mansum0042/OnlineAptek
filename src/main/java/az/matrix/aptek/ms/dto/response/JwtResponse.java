package az.matrix.aptek.ms.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse
{
    private String accessToken;
}
