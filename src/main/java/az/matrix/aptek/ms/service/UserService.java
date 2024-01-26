package az.matrix.aptek.ms.service;

import az.matrix.aptek.ms.dto.request.CreateUserRequest;
import az.matrix.aptek.ms.dto.request.UpdateUserRequest;
import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.enums.ERole;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.NotFoundException;

public interface UserService {
    UserResponse register(CreateUserRequest createUser) throws AlreadyExistException;

    UserResponse update(UpdateUserRequest updateUser) throws NotFoundException;

    UserResponse getById(Long id) throws NotFoundException;

    UserResponse getLoggedInUser() throws NotFoundException;

    UserResponse changeRole(ERole role) throws NotFoundException;
}
