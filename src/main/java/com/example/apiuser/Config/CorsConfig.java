package com.example.apiuser.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 1. Aplica a TODAS las rutas de la API
                        .allowedOrigins("http://localhost:5173", "http://localhost:4200", "http://localhost:3000") // 2. URLs permitidas (Frontend)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 3. Verbos permitidos
                        .allowedHeaders("*") // 4. Permitir todos los headers
                        .allowCredentials(true); // 5. Permitir cookies/autenticación si es necesario
            }
        };
    }
}