package sokpheng.com.blogapi;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sokpheng.com.blogapi.model.entities.Role;
import sokpheng.com.blogapi.model.repo.RoleRepository;

import java.util.UUID;

@Slf4j
@Controller // Use @Controller, not @RestController to return a view
@RequiredArgsConstructor
public class Home {
    private final RoleRepository roleRepository;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Blog API Service");
        model.addAttribute("status", "System Online");
        model.addAttribute("swaggerLink","/myagger");
        return "index"; // This looks for src/main/resources/templates/index.html
    }

}