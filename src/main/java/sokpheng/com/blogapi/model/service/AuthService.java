package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.mapper.UserMapper;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.repo.UserRepository;
import sokpheng.com.blogapi.security.PasswordEncoderConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final UserMapper userMapper;
    public UserResponseDto registerUser(CreateUserDto createUserDto){
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setFullName(createUserDto.fullName());
        user.setEmail(createUserDto.email());
        user.setPassword(passwordEncoderConfig
                .passwordEncoder()
                .encode(createUserDto.password()));
        user.setCreatedAt(LocalDateTime.now());
        // save
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }
}
