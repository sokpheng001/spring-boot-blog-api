package sokpheng.com.blogapi.model.dto;

import sokpheng.com.blogapi.model.entities.BlogStatus;

public record UpdateBlogDto(
        String title,
        String thumbnail,
        String content,
        BlogStatus status
) { }
