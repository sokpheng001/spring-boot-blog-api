package sokpheng.com.blogapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Use @Controller, not @RestController to return a view
public class Home {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Blog API Service");
        model.addAttribute("status", "System Online");
        model.addAttribute("swaggerLink","/myagger");
        return "index"; // This looks for src/main/resources/templates/index.html
    }
}