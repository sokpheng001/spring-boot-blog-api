package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;
import sokpheng.com.blogapi.mapper.CommentMapper;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.model.dto.CommentRequestDto;
import sokpheng.com.blogapi.model.dto.CommentResponseDto;
import sokpheng.com.blogapi.model.dto.UpdateCommentDto;
import sokpheng.com.blogapi.model.entities.Blog;
import sokpheng.com.blogapi.model.entities.Comment;
import sokpheng.com.blogapi.model.entities.User;
import sokpheng.com.blogapi.model.repo.BlogRepository;
import sokpheng.com.blogapi.model.repo.CommentRepository;
import sokpheng.com.blogapi.model.repo.UserRepository;

import javax.naming.CommunicationException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService  {
    final private CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public Page<CommentResponseDto> getAllCommentByBlogId(String blogUuid,
                                                          int pageNumber, int pageSize) {
        Blog blog = blogRepository.findBlogByUuid(blogUuid);
        if(blog==null){
            throw new SokphengNotFoundException("Blog is not Found");
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(
                "commentedDate").ascending());
        Page<Comment> commentPage = commentRepository
                .findCommentByBlog_Id(blog.getId(),pageable);
        return commentPage.map(commentMapper::toResponseDto);
    }





    public int deleteByUuid(String uuid) {
        Comment comment = commentRepository.findCommentByUuid(uuid);
        if(comment==null){
            throw new SokphengNotFoundException("Comment is not found");
        }
        commentRepository.delete(comment);
        return 1;
    }



    public CommentResponseDto createComment(Authentication authentication, CommentRequestDto o) {
        User user = userRepository.findUserByUuid(o.userUuid());

        if(user==null){
            throw new SokphengNotFoundException("User is not found");
        }
        if(!authentication.getPrincipal().equals(user.getUuid())){
            throw new SokphengNotFoundException("Current user is not able to comment, make sure user's uuid and the login user's uuid is the same");
        }
        log.info(o.blogUuid());
        Blog blog = blogRepository.findBlogByUuid(o.blogUuid());
        if(blog==null){
            throw new SokphengNotFoundException("Blog is not found");
        }
        Comment comment = new Comment();
        comment.setUuid(UUID.randomUUID().toString());
        comment.setBlog(blog);
        comment.setUser(user);
        comment.setCommentedDate(Date.from(Instant.now()));
        comment.setContent(o.content());
        // save
        commentRepository.save(comment);
        return commentMapper.toResponseDto(comment);
    }
    public CommentResponseDto updateComment(Authentication authentication,
                                            String uuid,
                                            UpdateCommentDto updateCommentDto){
        Comment comment = commentRepository.findCommentByUuid(uuid);
        if(comment==null){
            throw new SokphengNotFoundException("Comment is not found");
        }


        if(!comment.getUser().getUuid().equals(authentication.getPrincipal().toString())){
            throw new SokphengNotFoundException("U don't have permission to update the comment");
        }

        comment.setContent(updateCommentDto.newContent());
        // update
        commentRepository.save(comment);
        return commentMapper.toResponseDto(comment);
    }
}
