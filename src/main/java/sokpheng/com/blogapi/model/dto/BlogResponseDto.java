package sokpheng.com.blogapi.model.dto;

import lombok.Builder;
import sokpheng.com.blogapi.model.entities.BlogStatus;

import java.time.LocalDateTime;

@Builder
public record BlogResponseDto(
        String uuid,
        String title,
        String authorUuid,
        String thumbnailUrl,
        String content,
        BlogStatus status,
        LocalDateTime createdAt
) { }
