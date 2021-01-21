package ua.catalog.liki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.catalog.liki.util.ImageThumbPathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${uploads.path}")
    private String uploadsPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/images/204x177/*")
                .addResourceLocations(uploadsPath + "/images/204x177/")
                .resourceChain(true)
                .addResolver(new ImageThumbPathResourceResolver(204, 177, new ClassPathResource("static/watermark.png")));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/live").allowedOrigins("*");
            }
        };
    }

//    @Bean
//    public HttpTraceRepository htttpTraceRepository()
//    {
//        return new InMemoryHttpTraceRepository();
//    }

}
