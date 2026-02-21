package sokpheng.com.blogapi.model.dto;

import java.util.Date;

public record CommentResponseDto(
        String uuid,
        String content,
        Date commentedDate,
        UserResponseDto user
) {
}
