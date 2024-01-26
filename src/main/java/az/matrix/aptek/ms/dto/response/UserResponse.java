package az.matrix.aptek.ms.dto.response;

import az.matrix.aptek.ms.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private Integer id;
    private String name;
    private String surname;
    private ERole role;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    private String email;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdAt;
}
