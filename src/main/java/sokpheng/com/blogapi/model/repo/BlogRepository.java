package sokpheng.com.blogapi.model.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sokpheng.com.blogapi.model.entities.Blog;
import sokpheng.com.blogapi.model.entities.BlogStatus;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByStatus(BlogStatus blogStatus, Pageable pageable);
    Blog findBlogByUuid(String uuid);
    Page<Blog> findBlogByAuthorId(Long id, Pageable pageable);
}
