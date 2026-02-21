package sokpheng.com.blogapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.dto.RefreshTokenRequestDto;
import sokpheng.com.blogapi.model.dto.UserLoginDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.service.AuthService;
import sokpheng.com.blogapi.model.service.UserService;
import sokpheng.com.blogapi.utils.ResponseTemplate;
import sokpheng.com.blogapi.model.dto.TokenTemplate;

@RestController
@RequestMapping("/api/v100/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseTemplate<UserResponseDto> registerUser(@RequestBody @Valid CreateUserDto createUserDto){
        return new ResponseData<UserResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "User registered successfully",
                        authService.registerUser(createUserDto));
    }
    @PostMapping("/login")
    public ResponseTemplate<TokenTemplate> login(@RequestBody
                                                 UserLoginDto loginDto){
        return new ResponseData<TokenTemplate>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "User login successfully",
                        authService.loginUser(loginDto));
    }
    @PostMapping("/refresh")
    public ResponseTemplate<TokenTemplate> getNewToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return new ResponseData<TokenTemplate>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Get new token successfully",
                        authService.getNewToken(refreshTokenRequestDto));
    }
    @PostMapping("/me")
    public ResponseTemplate<UserResponseDto> getUserIUnfo(Authentication authentication) {
        return new ResponseData<UserResponseDto>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Get user information successfully",
                        authService.getUserInfo(authentication));
    }
}
