package sokpheng.com.blogapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.model.dto.CreateBlogDto;
import sokpheng.com.blogapi.model.dto.UpdateBlogDto;
import sokpheng.com.blogapi.model.entities.Category;
import sokpheng.com.blogapi.model.service.BlogService;
import sokpheng.com.blogapi.model.service.CategoryService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;


@Slf4j
@RestController
@RequestMapping("/api/v100/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;
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
    public ResponseTemplate<BlogResponseDto> createNewBlog(Authentication authentication,
                                                           @Valid @RequestBody CreateBlogDto o){
        Category category = new Category();
        category.setCategoryName(o.blogCategory().toUpperCase(Locale.ROOT));
        category.setCreatedAt(Date.from(Instant.now()));
        categoryService.createCategory(category);
        // this video is about the things
        return new ResponseData<BlogResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Created new blog",
                        blogService.create(authentication,o));
    }
    @GetMapping("/{uuid}")
    public ResponseTemplate<BlogResponseDto> getBlogUuid(
            @PathVariable String uuid){
        return new ResponseData<BlogResponseDto>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Get blog by uuid",
                        blogService.getByUuid(uuid));
    }
    @GetMapping("/user/{userUuid}")
    public ResponseTemplate<Page<BlogResponseDto>> getBlogByUserUuid(
            @PathVariable String userUuid, @RequestParam int pageNumber,
            @RequestParam int pageSize){
        return new ResponseData<Page<BlogResponseDto>>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Get blog by user uuid",
                        blogService.getBlogByUserUuid(userUuid,pageNumber, pageSize));
    }
    @PatchMapping("/{uuid}")
    public ResponseTemplate<BlogResponseDto> updateBlogByUuid(
            @PathVariable String uuid,
            @RequestBody UpdateBlogDto o){
        return new ResponseData<BlogResponseDto>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Updated a blog",
                        blogService.updateBlogByUuid(uuid,o));
    }
    @DeleteMapping("{uuid}")
    public ResponseTemplate<Integer> deleteBlogByUuid(@PathVariable String uuid ){
        return new ResponseData<Integer>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Deleted blog",
                        blogService.deleteByUuid(uuid));
    }
    @GetMapping("/category={c}")
    public ResponseTemplate<Page<BlogResponseDto>> getBlogByUserCategory(
            @PathVariable String c, @RequestParam int pageNumber,
            @RequestParam int pageSize){
        return new ResponseData<Page<BlogResponseDto>>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Get blog by blog category",
                        blogService.findBlogByCategory(c,pageNumber, pageSize));
    }
    @GetMapping("/title={t}")
    public ResponseTemplate<Page<BlogResponseDto>> getBlogByUserTitle(
            @PathVariable String t, @RequestParam int pageNumber,
            @RequestParam int pageSize){
        return new ResponseData<Page<BlogResponseDto>>()
                .get(String.valueOf(HttpStatus.CREATED.value()),
                        "Get blog by blog title",
                        blogService.findBlogByTitle(t,pageNumber, pageSize));
    }

}
