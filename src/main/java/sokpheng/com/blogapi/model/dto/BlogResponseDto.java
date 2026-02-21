package sokpheng.com.blogapi.model.dto;

import lombok.Builder;
import sokpheng.com.blogapi.model.entities.BlogStatus;
import sokpheng.com.blogapi.model.entities.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BlogResponseDto(
        String uuid,
        String title,
        String authorUuid,
        String thumbnailUrl,
        String content,
        Long view,
        BlogStatus status,
        LocalDateTime createdAt,
        List<CommentResponseDto> comments
) { }
