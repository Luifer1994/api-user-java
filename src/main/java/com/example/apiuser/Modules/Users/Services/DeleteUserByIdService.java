package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserByIdService {

    private final UserRepository userRepository;

    @Transactional
    public void execute(UUID id) { // Recibe UUID
        this.userRepository.deleteById(id);
    }
}