package az.matrix.aptek.ms.mapper;

import az.matrix.aptek.ms.dto.request.CreateUserRequest;
import az.matrix.aptek.ms.dto.request.UpdateUserRequest;
import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.entity.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    User map(CreateUserRequest source);

    UserResponse map(User source);

    void map (UpdateUserRequest source, @MappingTarget User target);
}
