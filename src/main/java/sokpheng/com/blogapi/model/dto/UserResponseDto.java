package sokpheng.com.blogapi.model.dto;

public record UserResponseDto(
        String  uuid,
        String fullName,
        String email,
        String profileUrl,
        String coverUrl,
        String bio
) {
}
