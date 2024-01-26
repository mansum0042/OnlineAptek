package az.matrix.aptek.ms.dao.impl;

import az.matrix.aptek.ms.dao.UserDao;
import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.entity.User;
import az.matrix.aptek.ms.enums.ERole;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.mapper.UserMapper;
import az.matrix.aptek.ms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDaoJpaImpl implements UserDao {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final UserMapper userMapper;

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("user.notFoundByEmail", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public User getById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("user.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public User getLoggedInUser() throws NotFoundException {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
            email = ((UserDetails) principal).getUsername();
        else
            email = principal.toString();
        return getByEmail(email);
    }

    @Override
    public UserResponse changeRole(ERole role) throws NotFoundException {
        User user = getLoggedInUser();
        user.setRole(role);
        user = userRepository.save(user);
        return userMapper.map(user);
    }
}
