package sokpheng.com.blogapi.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sokpheng.com.blogapi.model.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> { }
