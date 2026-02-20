package sokpheng.com.blogapi.model.dto;

public record UpdateUserDto(
        String fullName,
        String email,
        String profileUrl,
        String coverUrl,
        String bio
) { }
