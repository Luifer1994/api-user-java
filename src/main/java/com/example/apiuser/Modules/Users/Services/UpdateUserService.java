package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Exceptions.ResourceNotFoundException;
import com.example.apiuser.Modules.Users.Models.Dto.UserRequestDTO;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import com.example.apiuser.Modules.Users.Models.User;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO execute(UserRequestDTO request, UUID id) { // UUID

        User existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Verificación de email duplicado usando UUID
        if (!request.getEmail().equals(existingUser.getEmail()) &&
                this.userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DataIntegrityViolationException("The email is already in use by another user");
        }

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());

        User updatedUser = this.userRepository.save(existingUser);
        return UserResponseDTO.fromEntity(updatedUser);
    }
}