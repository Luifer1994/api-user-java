package com.example.apiuser.Modules.Users.Services;

import com.example.apiuser.Modules.Users.Models.Dto.UserFilter;
import com.example.apiuser.Modules.Users.Models.Dto.UserResponseDTO;
import com.example.apiuser.Modules.Users.Models.User;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.apiuser.Modules.Users.Models.Dto.UserMapper;

@Service
@RequiredArgsConstructor
public class GetAllUsersService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> execute(UserFilter filter, Pageable pageable) {
        Page<User> resultPage = userRepository.findAll(filter.toSpecification(), pageable);
        return resultPage.map(userMapper::toDTO);
    }
}