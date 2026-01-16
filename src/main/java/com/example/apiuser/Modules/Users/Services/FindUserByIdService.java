package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Exceptions.ResourceNotFoundException;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindUserByIdService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDTO execute(Long id) {
        return this.userRepository.findById(id)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}