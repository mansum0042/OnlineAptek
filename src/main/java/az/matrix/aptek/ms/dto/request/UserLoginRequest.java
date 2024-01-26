package az.matrix.aptek.ms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank(message = "{user.emptyEmail}")
    @Email(message = "{user.invalidEmail}")
    private String email;

    @NotBlank(message = "{user.emptyPassword}")
    @Size(min = 8, message = "{user.minPassword}")
    @Size(max = 13, message = "{user.maxPassword}")
    private String password;
}
