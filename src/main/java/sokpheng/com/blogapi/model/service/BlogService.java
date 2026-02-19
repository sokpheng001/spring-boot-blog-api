package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.mapper.BlogMapper;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;

import sokpheng.com.blogapi.model.dto.CreateBlogDto;
import sokpheng.com.blogapi.model.dto.UpdateBlogDto;
import sokpheng.com.blogapi.model.entities.Blog;
import sokpheng.com.blogapi.model.entities.BlogStatus;
import sokpheng.com.blogapi.model.repo.BlogRepository;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogService implements GlobalService<BlogResponseDto, CreateBlogDto> {
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    @Override
    public Page<BlogResponseDto> getAllDataByPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(
                "createdAt").ascending());
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return blogPage.map(blogMapper::toBlogResponse);
    }

    @Override
    public List<BlogResponseDto> getAll() {
        return blogRepository.findAll().stream().map(
                blogMapper::toBlogResponse
        ).toList();
    }

    @Override
    public BlogResponseDto getByUuid(String uuid) {
        return blogMapper.toBlogResponse(
                blogRepository.findBlogByUuid(uuid)
        );
    }

    @Override
    public int deleteByUuid(String uuid) {
        Blog blog = blogRepository.findBlogByUuid(uuid);
        blogRepository.delete(blog);
        return 1;
    }

    @Override
    public BlogResponseDto create(CreateBlogDto o) {
        Blog blog = new Blog();
        blog.setUuid(UUID.randomUUID().toString());
        blog.setTitle(o.title());
        blog.setContent(o.content());
        blog.setThumbnailUrl(o.thumbnail());
        blog.setCreatedAt(LocalDateTime.now());
        // save to database
        blogRepository.save(blog);
        return blogMapper.toBlogResponse(blog);
    }
    //
    public BlogResponseDto updateBlogByUuid(String uuid, UpdateBlogDto updateBlogDto){
        Blog blog = blogRepository.findBlogByUuid(uuid);
        if (blog==(null)){
            throw new RuntimeException("Blog is not found");
        }
        //
        if(updateBlogDto.status()!=null){
            // Check if the status is NOT in the allowed set
            if (!EnumSet.of(BlogStatus.DRAFT, BlogStatus.PUBLISHED).contains(updateBlogDto.status())) {
                throw new RuntimeException("Status of Blog must be DRAFT or PUBLISHED");
            }
            blog.setStatus(updateBlogDto.status());
        }
        if(updateBlogDto.thumbnail()!= null){
            blog.setThumbnailUrl(updateBlogDto.thumbnail());
        }
        if(updateBlogDto.content()!=null){
            blog.setContent(updateBlogDto.content());
        }
        if(updateBlogDto.title()!=null){
            blog.setTitle(updateBlogDto.title());
        }
         // update
        blogRepository.save(blog);
        return blogMapper.toBlogResponse(blog);
    }
}
