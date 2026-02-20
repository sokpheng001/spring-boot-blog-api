package sokpheng.com.blogapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.service.UserService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

@RestController
@RequestMapping("/api/v100/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseTemplate<Object> registerUser(@RequestBody
                                                 CreateUserDto createUserDto){
        return null;
    }
}
