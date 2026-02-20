package sokpheng.com.blogapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.model.dto.UpdateBlogDto;
import sokpheng.com.blogapi.model.dto.UpdateUserDto;
import sokpheng.com.blogapi.model.dto.UserResponseDto;
import sokpheng.com.blogapi.model.service.UserService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

@RequestMapping("/api/v100/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("")
    public ResponseTemplate<Page<UserResponseDto>> getBlogByPagination(
            @RequestParam(defaultValue = "0")  int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return new ResponseData<Page<UserResponseDto>>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Get All Users",
                        userService.getAllDataByPagination(pageNumber, pageSize)
                        );
    }
    @GetMapping("/{uuid}")
    public ResponseTemplate<UserResponseDto> getUserUuid(
            @PathVariable String uuid){
        return new ResponseData<UserResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Get user by uuid",
                        userService.getByUuid(uuid));
    }
    @PatchMapping("/{uuid}")
    public ResponseTemplate<UserResponseDto> updateBlogByUuid(
            @PathVariable String uuid,
            @RequestBody UpdateUserDto o){
        return new ResponseData<UserResponseDto>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Updated a user",
                        userService.updateUserByUuid(uuid,o));
    }
}
