package sokpheng.com.blogapi.model.dto;


public record CreateUserDto(
        String fullName,
        String email,
        String password
) { }
