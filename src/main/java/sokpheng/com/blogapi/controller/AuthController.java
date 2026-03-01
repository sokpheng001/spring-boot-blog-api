package sokpheng.com.blogapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.dto.RefreshTokenRequestDto;
import sokpheng.com.blogapi.model.dto.UserLoginDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.entities.VerificationToken;
import sokpheng.com.blogapi.model.repo.VerificationTokenRepository;
import sokpheng.com.blogapi.model.service.AuthService;
import sokpheng.com.blogapi.model.service.UserService;
import sokpheng.com.blogapi.utils.ResponseTemplate;
import sokpheng.com.blogapi.model.dto.TokenTemplate;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v100/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully!, you can login now");
    }
    @PostMapping("/register")
    public ResponseTemplate<String> registerUser(@RequestBody @Valid CreateUserDto createUserDto){
        authService.registerUser(createUserDto);
        return new ResponseData<String>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "User registered successfully",
                        "Please check and verify your email before login"
                        );
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
    @PostMapping("/admin")
    public ResponseTemplate<UserResponseDto> registerAdmin(@RequestBody @Valid CreateUserDto createUserDto){
        return new ResponseData<UserResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Admin registered successfully",
                        authService.registerAdmin(createUserDto));
    }
}
