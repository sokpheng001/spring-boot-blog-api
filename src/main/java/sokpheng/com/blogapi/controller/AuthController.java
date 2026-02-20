package sokpheng.com.blogapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.dto.UserLoginDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.service.AuthService;
import sokpheng.com.blogapi.model.service.UserService;
import sokpheng.com.blogapi.utils.ResponseTemplate;
import sokpheng.com.blogapi.utils.TokenTemplate;

@RestController
@RequestMapping("/api/v100/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseTemplate<UserResponseDto> registerUser(@RequestBody
                                                 CreateUserDto createUserDto){
        return new ResponseData<UserResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "User registered successfully",
                        authService.registerUser(createUserDto));
    }
    @GetMapping("/login")
    public ResponseTemplate<TokenTemplate> login(@RequestBody
                                                 UserLoginDto loginDto){
        return new ResponseData<TokenTemplate>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "User login successfully",
                        authService.loginUser(loginDto));
    }
}
