package sokpheng.com.blogapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.model.dto.CreateBlogDto;
import sokpheng.com.blogapi.model.dto.UpdateBlogDto;
import sokpheng.com.blogapi.model.service.BlogService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v100/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    @GetMapping
    public ResponseTemplate<Page<BlogResponseDto>> getBlogByPagination(
            @RequestParam(defaultValue = "0")  int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return new ResponseData<Page<BlogResponseDto>>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Get All Blogs",
                        blogService.getAllDataByPagination(pageNumber,pageSize));
    }
    @PostMapping()
    public ResponseTemplate<BlogResponseDto> createNewBlog(@RequestBody CreateBlogDto o){
        return new ResponseData<BlogResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Created new blog",
                        blogService.create(o));
    }
    @GetMapping("/{uuid}")
    public ResponseTemplate<BlogResponseDto> getBlogUuid(
            @PathVariable String uuid){
        return new ResponseData<BlogResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Get blog by uuid",
                        blogService.getByUuid(uuid));
    }
    @PatchMapping("/{uuid}")
    public ResponseTemplate<BlogResponseDto> updateBlogByUuid(
            @PathVariable String uuid,
            @RequestBody UpdateBlogDto o){
        return new ResponseData<BlogResponseDto>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Updated new blog",
                        blogService.updateBlogByUuid(uuid,o));
    }
    @DeleteMapping("{uuid}")
    public ResponseTemplate<Integer> createNewBlog(@PathVariable String uuid ){
        return new ResponseData<Integer>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Deleted blog",
                        blogService.deleteByUuid(uuid));
    }

}
