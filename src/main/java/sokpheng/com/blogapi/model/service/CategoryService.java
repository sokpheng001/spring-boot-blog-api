package sokpheng.com.blogapi.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.model.entities.Category;
import sokpheng.com.blogapi.model.repo.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }
}
