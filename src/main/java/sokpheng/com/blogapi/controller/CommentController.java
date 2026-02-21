package sokpheng.com.blogapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.CommentRequestDto;
import sokpheng.com.blogapi.model.dto.CommentResponseDto;
import sokpheng.com.blogapi.model.dto.UpdateCommentDto;
import sokpheng.com.blogapi.model.service.CommentService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

@RestController
@RequestMapping("/api/v100/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("")
    public ResponseTemplate<Page<CommentResponseDto>> getCommentsByBlogUuid(
            @RequestParam String blogUuid ,
            @RequestParam(defaultValue = "0")  int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return new ResponseData<Page<CommentResponseDto>>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Get All Comments by Blog id",
                        commentService.getAllCommentByBlogId(blogUuid,pageNumber,pageSize));
    }
    @PostMapping("")
    public ResponseTemplate<CommentResponseDto> createComment(
            Authentication authentication,
            @RequestBody CommentRequestDto commentRequestDto
            ){
        return new ResponseData<CommentResponseDto>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Comment on blog successfully",
                        commentService.createComment(authentication,commentRequestDto));
    }
    @DeleteMapping("{uuid}")
    public ResponseTemplate<Integer> deleteCommentByUuid(@PathVariable String uuid ){
        return new ResponseData<Integer>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Deleted comment successfully",
                        commentService.deleteByUuid(uuid));
    }
    @PatchMapping("/{uuid}")
    public ResponseTemplate<CommentResponseDto> updateCommentByUuid(
            Authentication authentication,
            @PathVariable String uuid,
            @RequestBody UpdateCommentDto updateCommentDto
            ){
        return new ResponseData<CommentResponseDto>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Comment on blog successfully",
                        commentService.updateComment(authentication,uuid, updateCommentDto));
    }
}
