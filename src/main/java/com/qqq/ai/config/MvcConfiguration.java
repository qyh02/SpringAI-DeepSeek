package com.qqq.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qqq
 * @DESCRIPTION
 * @createDate 2026/6/1 15:09
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ai/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Content-Disposition");;
    }
}
