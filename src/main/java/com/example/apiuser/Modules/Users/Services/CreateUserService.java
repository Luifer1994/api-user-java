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

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final com.example.apiuser.Utils.ServiceUrlResolver serviceUrlResolver;

    @Transactional
    public UserResponseDTO execute(UserRequestDTO request) {
        var newUser = request.toEntity();

        var savedUser = this.userRepository.save(newUser);

        // Call api-auth to create credentials
        // Resolves "auth" -> URI_AUTH and appends "/auth/register-internal"
        String registerUrl = serviceUrlResolver.resolve("auth/register-internal");

        Map<String, Object> registerRequest = new HashMap<>();
        registerRequest.put("userId", savedUser.getId());
        registerRequest.put("email", savedUser.getEmail());
        registerRequest.put("password", request.getPassword());

        try {
            restTemplate.postForObject(registerUrl, registerRequest, Object.class);
        } catch (Exception e) {
            System.err.println("Failed to register credentials: " + e.getMessage());
            throw new RuntimeException("Failed to register credentials: " + e.getMessage());
        }

        return UserResponseDTO.fromEntity(savedUser);
    }
}