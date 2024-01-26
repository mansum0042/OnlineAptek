package az.matrix.aptek.ms.dao;

import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.enums.ERole;
import az.matrix.aptek.ms.exception.NotFoundException;

public interface UserDao {
    User register(User user);

    User update(User user);

    User getByEmail(String email) throws NotFoundException;

    User getById(Long id) throws NotFoundException;

    User getLoggedInUser() throws NotFoundException;

    UserResponse changeRole(ERole role) throws NotFoundException;
}
