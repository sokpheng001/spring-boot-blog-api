package sokpheng.com.blogapi.model.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User implements UserDetails {
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
    private LocalDateTime createdAt;


    // Use OrphanRemoval to delete blogs if a user is deleted
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blog> blogs = new ArrayList<>();//
    //  roles set for users
    @ManyToMany(targetEntity = Role.class)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(e->new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.uuid;
    }
}