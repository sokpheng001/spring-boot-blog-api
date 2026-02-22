package sokpheng.com.blogapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@EnableWebMvc
public class FilePreviewConfig implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(FilePreviewConfig.class);
    @Value("${file.target}")
    private String targetFolder;
    @Value("${file.media-url}")
    private String previewUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("INfor file:" +  new File(targetFolder).getAbsoluteFile());
        registry.addResourceHandler(previewUrl + "/**")
                .addResourceLocations("file:"+ new File(targetFolder).getAbsoluteFile());
    }
}