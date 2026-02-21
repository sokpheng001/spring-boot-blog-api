package sokpheng.com.blogapi.model.service;

import com.fasterxml.jackson.annotation.JsonKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.exception.ExistingException;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;
import sokpheng.com.blogapi.mapper.UserMapper;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.dto.RefreshTokenRequestDto;
import sokpheng.com.blogapi.model.dto.UserLoginDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.entities.Role;
import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.repo.RoleRepository;
import sokpheng.com.blogapi.model.repo.UserRepository;
import sokpheng.com.blogapi.security.JWTUtils;
import sokpheng.com.blogapi.security.PasswordEncoderConfig;
import sokpheng.com.blogapi.model.dto.TokenTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtil;
    @Value("${jwt.access-token.expire}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh-token.expire}")
    private long refreshTokenExpiration;

    public UserResponseDto registerUser(CreateUserDto createUserDto){
        User existingUser = userRepository.findUserByEmail(createUserDto.email());
        if(existingUser!=null){
            throw new ExistingException("User with this email is existed");
        }
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setFullName(createUserDto.fullName());
        user.setEmail(createUserDto.email());
        // set role
        Role role = roleRepository.findRoleByName("USER");
        if(role==null){
            throw new SokphengNotFoundException("Role is not found for user");
        }
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoderConfig
                .passwordEncoder()
                .encode(createUserDto.password()));
        user.setCreatedAt(LocalDateTime.now());
        // save
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }
    public TokenTemplate loginUser(UserLoginDto userLoginDto){
        User user1  = userRepository.findUserByEmail(userLoginDto.email());
        if(user1==null){
            throw new SokphengNotFoundException("Email OR Password is wrong");
        }
        // verify password
        if(Objects.equals(passwordEncoderConfig.passwordEncoder()
                .encode(userLoginDto.password()), user1.getPassword())){
            throw new BadCredentialsException("Invalid email or password");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user1.getUuid(), userLoginDto.password())
        );

        String accessToken = jwtUtil.generateAccessToken(user1.getUuid(), authentication.getAuthorities());
        String refreshToken = jwtUtil.generateRefreshToken(user1.getUuid());
        return TokenTemplate.builder()
                .accessToken(accessToken)
                .accessTokenExpireInMilliSecond(accessTokenExpiration)
                .refreshToken(refreshToken)
                .refreshTokenExpireInMilliSecond(refreshTokenExpiration)
                .build();
    }
    public TokenTemplate getNewToken(RefreshTokenRequestDto refreshTokenRequestDto){
        assert refreshTokenRequestDto.refreshToken() != null;
        if(jwtUtil.isAccessTokenType(refreshTokenRequestDto.refreshToken())){
            throw new BadCredentialsException("This is not a valid refresh token");
        }
        boolean isTokenValid = jwtUtil.isTokenValid(refreshTokenRequestDto.refreshToken());// verify is valid token, it will throw an exception is not valid :)
        User user = userRepository.findUserByUuid(jwtUtil.extractSubject(refreshTokenRequestDto.refreshToken()));
        if(user==null){
            throw new SokphengNotFoundException("Invalid Refresh token");
        }
        String accessToken = jwtUtil.generateAccessToken(user.getUuid(), user.getAuthorities());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUuid());
        return TokenTemplate.builder()
                .accessToken(accessToken)
                .accessTokenExpireInMilliSecond(accessTokenExpiration)
                .refreshToken(newRefreshToken)
                .refreshTokenExpireInMilliSecond(refreshTokenExpiration)
                .build();
    }
    public UserResponseDto getUserInfo(Authentication authentication){
        User user = userRepository.findUserByUuid(authentication.getPrincipal().toString());
        if(user==null){
            throw new SokphengNotFoundException("User is not found");
        }
        return userMapper.toResponseDto(user);
    }
}
