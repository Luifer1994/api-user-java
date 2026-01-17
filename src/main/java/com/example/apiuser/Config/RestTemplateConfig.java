package com.example.apiuser.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            // Obtener el contexto de la petición actual (incoming request)
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();

            if (attributes != null) {
                // Extraer el token de la petición entrante
                String authToken = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

                // Si existe, agregarlo a la petición saliente (outgoing request)
                if (authToken != null) {
                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, authToken);
                }
            }

            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
