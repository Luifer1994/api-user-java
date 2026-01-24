package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Exceptions.ResourceNotFoundException;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import com.example.apiuser.Modules.Users.Models.Dto.UserMapper;

@Service
@RequiredArgsConstructor
public class FindUserByIdService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponseDTO execute(UUID id) {
        return this.userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
    }
}