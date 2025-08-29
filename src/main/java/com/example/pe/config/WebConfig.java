package com.example.pe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // CORS 허용 경로
                        .allowedOrigins("http://localhost:3000") // 프론트엔드 Origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용 메소드
                        .allowCredentials(true); // 인증 정보(쿠키) 허용
            }
        };
    }
}