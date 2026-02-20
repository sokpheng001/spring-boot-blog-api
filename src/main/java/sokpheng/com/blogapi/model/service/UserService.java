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

import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.repo.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements GlobalService<UserResponseDto, CreateUserDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Page<UserResponseDto> getAllDataByPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(
                "createdAt").ascending());
        Page<User> blogPage = userRepository.findAll(pageable);
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
        return null;
    }
}
