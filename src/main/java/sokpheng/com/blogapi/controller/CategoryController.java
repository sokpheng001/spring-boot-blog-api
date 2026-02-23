package sokpheng.com.blogapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sokpheng.com.blogapi.model.dto.BlogResponseDto;
import sokpheng.com.blogapi.model.entities.Category;
import sokpheng.com.blogapi.model.service.CategoryService;
import sokpheng.com.blogapi.utils.ResponseTemplate;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v100/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping()
    public ResponseTemplate<List<Category>> getAllCategories(){
        return new ResponseData<List<Category>>()
                .get(String.valueOf(HttpStatus.OK.value()),
                        "Get all categories",
                        categoryService.getAllCategories());
    }
}
