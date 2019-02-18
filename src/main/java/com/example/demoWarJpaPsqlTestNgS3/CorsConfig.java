package com.example.demoWarJpaPsqlTestNgS3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * testable sur http://192.32.133.162:8080/demoWarJpaPsqlTestNgS3/ index.html
 * 
 * @author x173117
 *
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer mvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "PUT", "POST", "GET", "OPTIONS")
                        .allowedHeaders(
                                "*,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,X-XSRF-TOKEN");
            }
        };
    }
}