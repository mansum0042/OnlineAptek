package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.dao.UserDao;
import az.matrix.aptek.ms.dto.request.CreateUserRequest;
import az.matrix.aptek.ms.dto.request.UpdateUserRequest;
import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.enums.ERole;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.mapper.UserMapper;
import az.matrix.aptek.ms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(CreateUserRequest createUser) throws AlreadyExistException {
        User user = userMapper.map(createUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(ERole.USER);

        checkEmailExist(user);
        user.setCreatedAt(new Date());

        return userMapper.map(userDao.register(user));
    }

    @Override
    public UserResponse update(UpdateUserRequest updateUser) throws NotFoundException {
        User user = userDao.getLoggedInUser();
        userMapper.map(updateUser, user);

        user = userDao.update(user);
        return userMapper.map(user);
    }

    @Override
    public UserResponse getById(Long id) throws NotFoundException {
        User user = userDao.getById(id);

        return userMapper.map(user);
    }

    @Override
    public UserResponse getLoggedInUser() throws NotFoundException {
        User user = userDao.getLoggedInUser();

        return userMapper.map(user);
    }

    @Override
    public UserResponse changeRole(ERole role) throws NotFoundException {
        return userDao.changeRole(role);
    }

    private void checkEmailExist(User user) throws AlreadyExistException {
        try {
            User foundUser = userDao.getByEmail(user.getEmail());
            if (foundUser != null && (user.getId() == null || foundUser.getId().equals(user.getId())))
                throw new AlreadyExistException(messageSource.getMessage("user.existByEmail", null,
                        LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }
}
