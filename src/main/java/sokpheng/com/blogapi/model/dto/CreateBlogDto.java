package sokpheng.com.blogapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import sokpheng.com.blogapi.model.entities.BlogStatus;


public record CreateBlogDto(
        String title,
        String thumbnail,
        BlogStatus status,
        String content
) { }
