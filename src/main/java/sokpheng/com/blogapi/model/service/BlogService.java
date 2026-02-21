package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;
import sokpheng.com.blogapi.mapper.BlogMapper;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;

import sokpheng.com.blogapi.model.dto.CreateBlogDto;
import sokpheng.com.blogapi.model.dto.UpdateBlogDto;
import sokpheng.com.blogapi.model.entities.Blog;
import sokpheng.com.blogapi.model.entities.BlogStatus;
import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.repo.BlogRepository;
import sokpheng.com.blogapi.model.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogService implements GlobalService<BlogResponseDto, CreateBlogDto> {
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final UserRepository userRepository;

    @Override
    public Page<BlogResponseDto> getAllDataByPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(
                "createdAt").descending());
        Page<Blog> blogPage = blogRepository.findByStatus(BlogStatus.PUBLISHED,pageable);
        return blogPage.map(blogMapper::toBlogResponse);
    }

    @Override
    public List<BlogResponseDto> getAll() {
        return blogRepository.findAll().stream().map(
                blogMapper::toBlogResponse
        ).toList();
    }
    public Page<BlogResponseDto> getBlogByUserUuid(String userUuid,int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(
                "createdAt").ascending());
        User user = userRepository.findUserByUuid(userUuid);
        if(user==null){
            throw new SokphengNotFoundException("User is not Found");
        }
        Page<Blog> blogPage = blogRepository.findBlogByAuthorId(user.getId(),pageable);
        return blogPage.map(blogMapper::toBlogResponse);
        //
    }
    @Override
    public BlogResponseDto getByUuid(String uuid) {
        Blog blog = blogRepository.findBlogByUuid(uuid);
        if(blog==null){
            throw new SokphengNotFoundException("Blog is not found");
        }
        Long oldView = blog.getView();
        // update view when clicked on the blog
        blog.setView(oldView+1);
        blogRepository.save(blog);
        return blogMapper.toBlogResponse(
                blog
        );
    }

    @Override
    public int deleteByUuid(String uuid) {
        Blog blog = blogRepository.findBlogByUuid(uuid);
        if(blog==null){
            throw new SokphengNotFoundException("Blog is not found");
        }
        blogRepository.delete(blog);
        return 1;
    }

    @Override
    public BlogResponseDto create(CreateBlogDto o) {
        return null;
    }


    public BlogResponseDto create(Authentication authentication,CreateBlogDto o) {
        // get uuid from token for setting who created the blog

        String userUuid = Objects.requireNonNull(authentication.getPrincipal()).toString();
        User user = userRepository.findUserByUuid(userUuid);
        if(user==null){
            throw new SokphengNotFoundException("Invalid token");
        }
        Blog blog = new Blog();
        blog.setAuthor(user);
        blog.setAuthorUuid(user.getUuid());
        blog.setUuid(UUID.randomUUID().toString());
        blog.setTitle(o.title());
        blog.setContent(o.content());
        blog.setThumbnailUrl(o.thumbnail());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setStatus(o.status());
        blog.setView(0L);
        // save to database
        blogRepository.save(blog);
        return blogMapper.toBlogResponse(blog);
    }
    //
    public BlogResponseDto updateBlogByUuid(String uuid, UpdateBlogDto updateBlogDto){
        Blog blog = blogRepository.findBlogByUuid(uuid);
        if (blog==(null)){
            throw new SokphengNotFoundException("Blog is not found");
        }
        //
        if(updateBlogDto.status()!=null){
            // Check if the status is NOT in the allowed set
            if (!EnumSet.of(BlogStatus.DRAFT, BlogStatus.PUBLISHED).contains(updateBlogDto.status())) {
                throw new HttpMessageNotReadableException("Blog must be in DRAFT or PUBLISHED ", null );
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
