package az.matrix.aptek.ms.controller;

import az.matrix.aptek.ms.dto.request.CreateUserRequest;
import az.matrix.aptek.ms.dto.request.UserLoginRequest;
import az.matrix.aptek.ms.dto.response.BaseResponse;
import az.matrix.aptek.ms.dto.response.JwtResponse;
import az.matrix.aptek.ms.dto.response.UserResponse;
import az.matrix.aptek.ms.exception.AlreadyExistException;
import az.matrix.aptek.ms.exception.NotFoundException;
import az.matrix.aptek.ms.service.JwtService;
import az.matrix.aptek.ms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody @Valid CreateUserRequest request)
            throws AlreadyExistException {
        UserResponse userResponse = userService.register(request);

        BaseResponse<UserResponse> baseResponse = BaseResponse.<UserResponse>builder().data(userResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@RequestBody @Valid UserLoginRequest request)
            throws NotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));

        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(jwtService.generateAccessToken(request.getEmail()))
                .build();

        BaseResponse<JwtResponse> baseResponse = BaseResponse.<JwtResponse>builder().data(jwtResponse).build();
        return ResponseEntity.ok(baseResponse);
    }
}
