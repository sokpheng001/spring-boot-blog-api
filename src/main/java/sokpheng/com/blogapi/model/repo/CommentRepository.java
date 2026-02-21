package sokpheng.com.blogapi.model.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sokpheng.com.blogapi.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findCommentByBlog_Id(Long id,Pageable pageable);
    Comment findCommentByUuid(String uuid);
}
