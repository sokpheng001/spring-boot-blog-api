package sokpheng.com.blogapi.model.dto;

import lombok.Builder;

@Builder
public record FileResponseDto(
        String fileName,
        Long size,
        String previewLink
) {
}
