package az.matrix.aptek.ms.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest
{
    @NotBlank(message = "{user.emptyName}")
    private String name;

    @NotBlank(message = "{user.emptySurname}")
    private String surname;

    @NotBlank(message = "{user.emptyEmail}")
    @Email(message = "{user.invalidEmail}")
    private String email;

    @NotBlank(message = "{user.emptyPassword}")
    @Size(min = 8, message = "{user.minPassword}")
    @Size(max = 13, message = "{user.maxPassword}")
    private String password;
}
