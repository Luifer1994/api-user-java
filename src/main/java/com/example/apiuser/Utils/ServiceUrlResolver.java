package com.example.apiuser.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceUrlResolver {

    @Value("${URI_AUTH:http://ms-auth:8081}")
    private String authUrl;

    @Value("${URI_USER:http://ms-users:8082}")
    private String userUrl;

    /**
     * Resuelve la URL completa basada en el primer segmento del path.
     * Ejemplo: "auth/login" -> "http://ms-auth:8081/auth/login"
     */
    public String resolve(String path) {
        // Asegurar que no empiece con / para facilitar el split, o manejarlo
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;

        String[] segments = cleanPath.split("/");
        if (segments.length == 0) {
            throw new IllegalArgumentException("Path invalid");
        }

        String service = segments[0];

        String baseUrl = switch (service) {
            case "auth" -> authUrl;
            case "users" -> userUrl;
            default -> throw new IllegalArgumentException("Unknown service alias: " + service);
        };

        // Construir URL final.
        // Si baseUrl ya tiene slash final, evitar duplicarlo.
        // URI vars suelen venir sin slash final (http://host:port)
        return baseUrl + "/" + cleanPath;
    }
}
