package sokpheng.com.blogapi.model.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String profileUrl;
    private String coverUrl;
    private String fullName;
    private String bio;


    // Use OrphanRemoval to delete blogs if a user is deleted
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blog> blogs = new ArrayList<>();
}