package sokpheng.com.blogapi.model.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sokpheng.com.blogapi.model.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAll(Pageable pageable);
    Blog findBlogByUuid(String uuid);
}
