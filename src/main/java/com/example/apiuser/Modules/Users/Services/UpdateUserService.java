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

import com.example.apiuser.Modules.Users.Models.Dto.UserMapper;

@Service
@RequiredArgsConstructor
public class UpdateUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO execute(UserRequestDTO request, UUID id) {

        User existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        // Verificación de email duplicado usando UUID
        if (!request.getEmail().equals(existingUser.getEmail()) &&
                this.userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DataIntegrityViolationException("user.email.already");
        }

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());

        if (request.getAddresses() != null) {
            existingUser.getAddresses().clear();
            var newAddresses = request.getAddresses().stream()
                    .map(com.example.apiuser.Modules.Addresses.Models.Dto.AddressMapper.INSTANCE::toEntity)
                    .toList();
            newAddresses.forEach(address -> address.setUser(existingUser));
            existingUser.getAddresses().addAll(newAddresses);
        }

        User updatedUser = this.userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }
}