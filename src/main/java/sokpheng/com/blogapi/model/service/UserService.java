package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;
import sokpheng.com.blogapi.mapper.UserMapper;
import sokpheng.com.blogapi.model.dto.CreateUserDto;
import sokpheng.com.blogapi.model.dto.UpdateUserDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;

import sokpheng.com.blogapi.model.entities.Role;
import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.repo.RoleRepository;
import sokpheng.com.blogapi.model.repo.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements GlobalService<UserResponseDto, CreateUserDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public Page<UserResponseDto> getAllDataByPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(
                "createdAt").ascending());
        Role role = roleRepository.findRoleByName("USER");
        if (role==null){
            throw new SokphengNotFoundException("Check for role for getting all users");
        }
        Page<User> blogPage = userRepository.findAllByRoles_Name("USER",pageable);
        return blogPage.map(userMapper::toResponseDto);
    }

    @Override
    public List<UserResponseDto> getAll() {
        return List.of();
    }

    @Override
    public UserResponseDto getByUuid(String uuid) {
        User user = userRepository.findUserByUuid(
                uuid
        );
        if(user==null){
            throw new SokphengNotFoundException("User is not Found");
        }
        return userMapper.toResponseDto(
                user
        );
    }

    @Override
    public int deleteByUuid(String uuid) {
        User user = userRepository.findUserByUuid(uuid);
        if(user==null){
            throw new SokphengNotFoundException("User is not Found");
        }
        // delete
        userRepository.delete(user);
        return 1;
    }

    @Override
    public UserResponseDto create(CreateUserDto o) {
        return null;
    }
    public UserResponseDto updateUserByUuid(String uuid,
                                            UpdateUserDto updateUserDto){
        getByUuid(uuid);
        User user  = userRepository.findUserByUuid(uuid);
        if(updateUserDto.fullName()!=null){
            user.setFullName(updateUserDto.fullName());
        }
        if(updateUserDto.bio()!=null){
            user.setBio(updateUserDto.bio());
        }
        if(updateUserDto.email()!=null){
            user.setEmail(updateUserDto.email());
        }
        if(updateUserDto.coverUrl()!=null){
            user.setCoverUrl(updateUserDto.coverUrl());
        }
        if(updateUserDto.profileUrl()!=null){
            user.setProfileUrl(updateUserDto.profileUrl());
        }
        // update
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }
}
