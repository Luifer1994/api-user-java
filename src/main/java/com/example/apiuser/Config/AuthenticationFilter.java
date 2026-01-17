package com.example.apiuser.Config;

import com.example.apiuser.Utils.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final SecretKey KEY = Keys.hmacShaKeyFor(
            "ESTA_ES_UNA_LLAVE_MUY_SECRETA_Y_LARGA_PARA_SEGURIDAD_123456".getBytes(StandardCharsets.UTF_8));

    // Configurado para que el timestamp no sea un array de números
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private final MessageSource messageSource;

    public AuthenticationFilter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(7);
                Claims claims = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
                String email = claims.get("email", String.class);

                if (email != null) {
                    var auth = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                sendErrorResponse(response, request, "security.token.invalid", HttpStatus.UNAUTHORIZED);
                return;
            }
        }

        // Bloqueo manual para rutas protegidas sin auth (acceso directo puerto 8082)
        if (request.getRequestURI().startsWith("/users")
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            sendErrorResponse(response, request, "security.auth.required", HttpStatus.UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpServletRequest request, String key,
            HttpStatus status) throws IOException {
        java.util.Locale locale = request.getLocale();
        String message = messageSource.getMessage(key, null, key, locale);

        var entity = HttpResponse.send(message, status);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(entity.getBody()));
    }
}