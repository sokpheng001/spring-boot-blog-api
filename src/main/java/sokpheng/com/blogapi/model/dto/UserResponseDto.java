package sokpheng.com.blogapi.model.dto;

import java.time.LocalDateTime;

public record UserResponseDto(
        String  uuid,
        String fullName,
        String email,
        String profileUrl,
        String coverUrl,
        String bio,
        LocalDateTime createdAt
) {
}
