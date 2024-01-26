package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.UpdateUserRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.enums.ERole;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> getMyInformation() throws NotFoundException {
        UserResponse userResponse = userService.getLoggedInUser();

        BaseResponse<UserResponse> baseResponse = BaseResponse.<UserResponse>builder().data(userResponse).build();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getById(
            @PathVariable Long id) throws NotFoundException {
        UserResponse userResponse = userService.getById(id);

        BaseResponse<UserResponse> baseResponse = BaseResponse.<UserResponse>builder().data(userResponse).build();
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping
    public ResponseEntity<BaseResponse<UserResponse>> update(@RequestBody UpdateUserRequest request)
            throws NotFoundException {
        UserResponse userResponse = userService.update(request);

        BaseResponse<UserResponse> baseResponse = BaseResponse.<UserResponse>builder().data(userResponse).build();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/becomeSeller")
    public ResponseEntity<BaseResponse<UserResponse>> becomeSeller() throws NotFoundException {
        return changeRole(ERole.SELLER);
    }

    @PostMapping("/becomeUser")
    public ResponseEntity<BaseResponse<UserResponse>> becomeUser() throws NotFoundException {
        return changeRole(ERole.USER);
    }

    private ResponseEntity<BaseResponse<UserResponse>> changeRole(ERole role) throws NotFoundException {
        UserResponse userResponse = userService.changeRole(role);
        BaseResponse<UserResponse> baseResponse = BaseResponse.<UserResponse>builder()
                .data(userResponse)
                .successMsg(messageSource.getMessage(
                        role == ERole.SELLER ? "user.becomeSeller" : "user.becomeUser",
                        null, LocaleContextHolder.getLocale()))
                .build();
        return ResponseEntity.ok(baseResponse);
    }
}
