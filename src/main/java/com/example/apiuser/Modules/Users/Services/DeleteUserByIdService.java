package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserByIdService {

    private final UserRepository userRepository;

    @Transactional
    public void execute(Long id) {
        this.userRepository.deleteById(id);
    }
}