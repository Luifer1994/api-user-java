package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Modules.Users.Models.Dto.UserRequestDTO;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO execute(UserRequestDTO request) {

        var newUser = request.toEntity();

        var savedUser = this.userRepository.save(newUser);

        return UserResponseDTO.fromEntity(savedUser);
    }
}