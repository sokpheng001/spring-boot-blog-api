package sokpheng.com.blogapi.mapper;


import org.mapstruct.Mapper;

import org.springframework.data.web.config.EnableSpringDataWebSupport;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.model.entities.Blog;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    BlogResponseDto toBlogResponse(Blog blog);

}