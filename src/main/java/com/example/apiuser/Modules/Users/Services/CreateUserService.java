package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Modules.Users.Models.Dto.UserRequestDTO;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.Map;


import com.example.apiuser.Modules.Users.Models.Dto.UserMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final com.example.apiuser.Utils.ServiceUrlResolver serviceUrlResolver;
    private final UserMapper userMapper;
    

    @Transactional
    public UserResponseDTO execute(UserRequestDTO request) {
        var newUser = userMapper.toEntity(request);

        if (newUser.getAddresses() != null) {
            newUser.getAddresses().forEach(address -> address.setUser(newUser));
        }

        var savedUser = this.userRepository.save(newUser);

        // Call api-auth to create credentials
        // Resolves "auth" -> URI_AUTH and appends "/auth/register-internal"
        String registerUrl = serviceUrlResolver.resolve("auth/register-internal");

        Map<String, Object> registerRequest = new HashMap<>();
        registerRequest.put("userId", savedUser.getId());
        registerRequest.put("email", savedUser.getEmail());
        registerRequest.put("password", request.getPassword());

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Extract Authorization header from current request
            var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String authHeader = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                if (authHeader != null) {
                    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
                }
            }

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(registerRequest, headers);
            
            restTemplate.postForEntity(registerUrl, entity, Object.class);
        } catch (Exception e) {
            System.err.println("Failed to register credentials: " + e.getMessage());
            throw new RuntimeException("Failed to register credentials: " + e.getMessage());
        }

        return userMapper.toDTO(savedUser);
    }
}