package sokpheng.com.blogapi.model.dto;

public record CommentRequestDto(
        String blogUuid,
        String userUuid,
        String content
) {
}
