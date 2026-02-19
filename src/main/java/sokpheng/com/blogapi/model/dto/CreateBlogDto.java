package sokpheng.com.blogapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


public record CreateBlogDto(
        String title,
        String thumbnail,
        String content
) { }
