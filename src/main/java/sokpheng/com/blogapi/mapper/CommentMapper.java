package sokpheng.com.blogapi.mapper;

import org.mapstruct.Mapper;
import sokpheng.com.blogapi.model.dto.CommentResponseDto;
import sokpheng.com.blogapi.model.entities.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDto toResponseDto(Comment comment);
}
